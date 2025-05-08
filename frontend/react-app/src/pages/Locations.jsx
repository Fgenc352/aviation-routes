import React, { useEffect, useState } from 'react';
import { locationService } from '../services/api';
import './Locations.css';

export default function Locations() {
    const [list, setList]     = useState([]);
    const [form, setForm]     = useState({ name:'', country:'', city:'', locationCode:'' });
    const [editId, setEditId] = useState(null);

    const [filterName, setFilterName]       = useState('');
    const [filterCountry, setFilterCountry] = useState('');
    const [filterCity, setFilterCity]       = useState('');
    const [filterCode, setFilterCode]       = useState('');

    useEffect(refresh, []);

    function refresh() {
        locationService.list()
            .then(res => setList(res.data))
            .catch(err => console.error('GET error', err));
    }

    function resetForm() {
        setEditId(null);
        setForm({ name:'', country:'', city:'', locationCode:'' });
    }

    function save() {
        const missing = [];
        if (!form.name.trim())         missing.push('Name');
        if (!form.country.trim())      missing.push('Country');
        if (!form.city.trim())         missing.push('City');
        if (!form.locationCode.trim()) missing.push('Code');
        if (missing.length) {
            alert(`Please fill: ${missing.join(', ')}`);
            return;
        }

        const call = editId
            ? locationService.update(editId, form)
            : locationService.create(form);

        call
            .then(() => {
                resetForm();
                refresh();
            })
            .catch(err => {
                const msg =
                    err.response?.data?.message
                    || err.message
                    || 'Unknown error';
                console.error('SAVE error', err);
                alert(`Save error: ${msg}`);
            });
    }

    function remove(id) {
        if (!window.confirm('Silmek istediğine emin misin?')) return;
        locationService.remove(id)
            .then(refresh)
            .catch(err => {
                const msg = err.response?.data?.message || err.message;
                console.error('DELETE error', err);
                alert(`Silme hatası: ${msg}`);
            });
    }

    function onEdit(loc) {
        setEditId(loc.id);
        setForm({
            name:         loc.name,
            country:      loc.country,
            city:         loc.city,
            locationCode: loc.locationCode
        });
    }

    const names     = Array.from(new Set(list.map(l => l.name))).sort();
    const countries = Array.from(new Set(list.map(l => l.country))).sort();
    const cities    = Array.from(new Set(list.map(l => l.city))).sort();
    const codes     = Array.from(new Set(list.map(l => l.locationCode))).sort();

    const filtered = list.filter(loc => {
        if (filterName    && loc.name         !== filterName   ) return false;
        if (filterCountry && loc.country      !== filterCountry) return false;
        if (filterCity    && loc.city         !== filterCity   ) return false;
        if (filterCode    && loc.locationCode !== filterCode   ) return false;
        return true;
    });

    return (
        <div className="locations-container">
            <h2>Locations</h2>

            <div className="filter-row">
                <label>
                    Name
                    <select value={filterName} onChange={e => setFilterName(e.target.value)}>
                        <option value="">All</option>
                        {names.map(n => <option key={n} value={n}>{n}</option>)}
                    </select>
                </label>
                <label>
                    Country
                    <select value={filterCountry} onChange={e => setFilterCountry(e.target.value)}>
                        <option value="">All</option>
                        {countries.map(c => <option key={c} value={c}>{c}</option>)}
                    </select>
                </label>
                <label>
                    City
                    <select value={filterCity} onChange={e => setFilterCity(e.target.value)}>
                        <option value="">All</option>
                        {cities.map(c => <option key={c} value={c}>{c}</option>)}
                    </select>
                </label>
                <label>
                    Code
                    <select value={filterCode} onChange={e => setFilterCode(e.target.value)}>
                        <option value="">All</option>
                        {codes.map(code => <option key={code} value={code}>{code}</option>)}
                    </select>
                </label>
            </div>

            <div className="form-row">
                {['name','country','city','locationCode'].map(f => (
                    <label key={f}>
                        {f === 'locationCode' ? 'Code' : f.charAt(0).toUpperCase() + f.slice(1)}
                        <input
                            value={form[f]}
                            onChange={e => setForm({ ...form, [f]: e.target.value })}
                            placeholder={f === 'locationCode' ? 'Code' : f}
                        />
                    </label>
                ))}
                <button onClick={save}>
                    {editId ? 'Update' : 'Create'}
                </button>
                {editId && (
                    <button onClick={resetForm} className="cancel-btn">
                        Cancel
                    </button>
                )}
            </div>

            <table className="table">
                <thead>
                <tr>
                    <th>Name</th>
                    <th>Country</th>
                    <th>City</th>
                    <th>Code</th>
                    <th>Actions</th>
                </tr>
                </thead>
                <tbody>
                {filtered.length === 0 && (
                    <tr>
                        <td colSpan="5" className="no-data">
                            No locations found.
                        </td>
                    </tr>
                )}
                {filtered.map(loc => (
                    <tr key={loc.id}>
                        <td>{loc.name}</td>
                        <td>{loc.country}</td>
                        <td>{loc.city}</td>
                        <td>{loc.locationCode}</td>
                        <td>
                            <button onClick={() => onEdit(loc)}>Edit</button>
                            <button onClick={() => remove(loc.id)} className="del">
                                Del
                            </button>
                        </td>
                    </tr>
                ))}
                </tbody>
            </table>
        </div>
    );
}
