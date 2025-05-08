import React, { useEffect, useState } from 'react';
import { transportationService, locationService } from '../services/api';
import './Transportations.css';

export default function Transportations() {
    const [list, setList] = useState([]);
    const [locs, setLocs] = useState([]);

    const [form, setForm] = useState({
        originId: '',
        destinationId: '',
        type: 'FLIGHT',
        operatingDays: []
    });
    const [editId, setEditId] = useState(null);

    const [filterOrigin, setFilterOrigin] = useState('');
    const [filterDest, setFilterDest] = useState('');
    const [filterType, setFilterType] = useState('');
    const [filterDays, setFilterDays] = useState([]);

    useEffect(() => {
        locationService.list().then(r => setLocs(r.data));
        load();
    }, []);

    function load() {
        transportationService.list().then(r => setList(r.data));
    }

    function resetForm() {
        setEditId(null);
        setForm({
            originId: '',
            destinationId: '',
            type: 'FLIGHT',
            operatingDays: []
        });
    }

    function save() {
        const missing = [];
        if (!form.originId)                missing.push("Origin");
        if (!form.destinationId)           missing.push("Destination");
        if (!form.type)                    missing.push("Type");
        if (form.operatingDays.length === 0) missing.push("Operating Days");
        if (missing.length > 0) {
            alert("Please fill: " + missing.join(", "));
            return;
        }

        const origin = locs.find(l => l.id === +form.originId);
        const dest   = locs.find(l => l.id === +form.destinationId);
        const dto = {
            originCode:         origin.locationCode,
            destinationCode:    dest.locationCode,
            transportationType: form.type,
            operatingDays:      form.operatingDays.map(d => +d)
        };

        const call = editId
            ? transportationService.update(editId, dto)
            : transportationService.create(dto);

        call
            .then(() => {
                resetForm();
                load();
            })
            .catch(err => {
                const msg = err.response?.data?.message
                    || err.response?.statusText
                    || err.message;
                alert(msg);
            });
    }

    function remove(id) {
        if (!window.confirm('Silmek istediğine emin misin?')) return;
        transportationService
            .remove(id)
            .then(load)
            .catch(err => {
                const msg = err.response?.data?.message
                    || err.message;
                alert("Delete failed: " + msg);
            });
    }

    function onEdit(tx) {
        setEditId(tx.id);
        setForm({
            originId:      String(locs.find(l => l.locationCode === tx.originCode).id),
            destinationId: String(locs.find(l => l.locationCode === tx.destinationCode).id),
            type:          tx.transportationType,
            operatingDays: [...tx.operatingDays]
        });
    }

    function toggleFormDay(day) {
        setForm(f => {
            const has = f.operatingDays.includes(day);
            return {
                ...f,
                operatingDays: has
                    ? f.operatingDays.filter(d => d !== day)
                    : [...f.operatingDays, day]
            };
        });
    }

    function toggleFilterDay(day) {
        setFilterDays(fd => {
            const has = fd.includes(day);
            return has ? fd.filter(d => d !== day) : [...fd, day];
        });
    }

    const filtered = list.filter(tx => {
        if (filterOrigin) {
            const oc = locs.find(l => l.id === +filterOrigin)?.locationCode;
            if (tx.originCode !== oc) return false;
        }
        if (filterDest) {
            const dc = locs.find(l => l.id === +filterDest)?.locationCode;
            if (tx.destinationCode !== dc) return false;
        }
        if (filterType && tx.transportationType !== filterType) return false;
        if (filterDays.length > 0 &&
            !filterDays.every(d => tx.operatingDays.includes(d))) {
            return false;
        }
        return true;
    });

    return (
        <div>
            <h2>Transportations</h2>

            <div className="form-row">
                <label>
                    From
                    <select
                        value={form.originId}
                        onChange={e => setForm({ ...form, originId: e.target.value })}
                    >
                        <option value="">-- origin --</option>
                        {locs.map(l =>
                            <option key={l.id} value={l.id}>
                                {l.locationCode} — {l.name}
                            </option>
                        )}
                    </select>
                </label>

                <label>
                    To
                    <select
                        value={form.destinationId}
                        onChange={e => setForm({ ...form, destinationId: e.target.value })}
                    >
                        <option value="">-- destination --</option>
                        {locs.map(l =>
                            <option key={l.id} value={l.id}>
                                {l.locationCode} — {l.name}
                            </option>
                        )}
                    </select>
                </label>

                <label>
                    Type
                    <select
                        value={form.type}
                        onChange={e => setForm({ ...form, type: e.target.value })}
                    >
                        {['FLIGHT','BUS','SUBWAY','UBER'].map(t =>
                            <option key={t} value={t}>{t}</option>
                        )}
                    </select>
                </label>

                <div className="days-selector">
                    {Array.from({ length: 7 }, (_, i) => i + 1).map(d =>
                        <label key={d}>
                            <input
                                type="checkbox"
                                checked={form.operatingDays.includes(d)}
                                onChange={() => toggleFormDay(d)}
                            /> {d}
                        </label>
                    )}
                </div>

                <button onClick={save}>
                    {editId ? 'Update' : 'Create'}
                </button>
                {editId &&
                    <button onClick={resetForm}>Cancel</button>
                }
            </div>

            <div className="filter-row">
                <label>
                    Origin
                    <select
                        value={filterOrigin}
                        onChange={e => setFilterOrigin(e.target.value)}
                    >
                        <option value="">All</option>
                        {locs.map(l =>
                            <option key={l.id} value={l.id}>{l.locationCode}</option>
                        )}
                    </select>
                </label>

                <label>
                    Destination
                    <select
                        value={filterDest}
                        onChange={e => setFilterDest(e.target.value)}
                    >
                        <option value="">All</option>
                        {locs.map(l =>
                            <option key={l.id} value={l.id}>{l.locationCode}</option>
                        )}
                    </select>
                </label>

                <label>
                    Type
                    <select
                        value={filterType}
                        onChange={e => setFilterType(e.target.value)}
                    >
                        <option value="">All</option>
                        {['FLIGHT','BUS','SUBWAY','UBER'].map(t =>
                            <option key={t} value={t}>{t}</option>
                        )}
                    </select>
                </label>

                <div className="days-selector">
                    {Array.from({ length: 7 }, (_, i) => i + 1).map(d =>
                        <label key={d}>
                            <input
                                type="checkbox"
                                checked={filterDays.includes(d)}
                                onChange={() => toggleFilterDay(d)}
                            /> {d}
                        </label>
                    )}
                </div>
            </div>

            <table className="table">
                <thead>
                <tr>
                    <th>From</th>
                    <th>To</th>
                    <th>Type</th>
                    <th>Days</th>
                    <th>Actions</th>
                </tr>
                </thead>
                <tbody>
                {filtered.map(tx =>
                    <tr key={tx.id}>
                        <td>{tx.originCode}</td>
                        <td>{tx.destinationCode}</td>
                        <td>{tx.transportationType}</td>
                        <td>{tx.operatingDays.join(',')}</td>
                        <td>
                            <button className="edit" onClick={() => onEdit(tx)}>Edit</button>
                            <button className="del" onClick={() => remove(tx.id)}>Del</button>
                        </td>
                    </tr>
                )}
                </tbody>
            </table>
        </div>
    );
}
