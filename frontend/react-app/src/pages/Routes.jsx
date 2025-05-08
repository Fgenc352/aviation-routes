import React, { useEffect, useState } from 'react';
import { routeService, locationService } from '../services/api';
import RouteSidePanel from '../components/RouteSidePanel';
import './Routes.css';

export default function RoutesPage() {
    const [locs, setLocs] = useState([]);
    const [origin, setOrigin] = useState('');
    const [destination, setDestination] = useState('');
    const [date, setDate] = useState('');
    const [routes, setRoutes] = useState([]);
    const [selectedIndex, setSelectedIndex] = useState(null);

    useEffect(() => {
        locationService.list().then(r => setLocs(r.data));
        routeService.search('', '', '').then(r => setRoutes(r.data)).catch(() => {});
    }, []);

    function search() {
        routeService
            .search(origin, destination, date)
            .then(res => {
                setRoutes(res.data);
                setSelectedIndex(null);
            })
            .catch(() => {
                setRoutes([]);
                setSelectedIndex(null);
            });
    }

    function getViaLabel(route) {
        const flight = route.segments.find(s => s.transportationType === 'FLIGHT');
        if (!flight) return 'Direct';
        const loc = locs.find(l => l.locationCode === flight.originCode);
        return `Via ${loc?.name || flight.originCode} (${flight.originCode})`;
    }

    return (
        <div className="routes-container">
            <h2>Routes</h2>

            <div className="form-row">
                <label>
                    Origin
                    <select value={origin} onChange={e => setOrigin(e.target.value)}>
                        <option value="">All</option>
                        {locs.map(l => (
                            <option key={l.locationCode} value={l.locationCode}>
                                {l.name} ({l.locationCode})
                            </option>
                        ))}
                    </select>
                </label>

                <label>
                    Destination
                    <select value={destination} onChange={e => setDestination(e.target.value)}>
                        <option value="">All</option>
                        {locs.map(l => (
                            <option key={l.locationCode} value={l.locationCode}>
                                {l.name} ({l.locationCode})
                            </option>
                        ))}
                    </select>
                </label>

                <label>
                    Date
                    <input type="date" value={date} onChange={e => setDate(e.target.value)} />
                </label>

                <button onClick={search}>Search</button>
            </div>

            <h4>Available Routes</h4>
            <ul className="available-list">
                {routes.length === 0 && <li className="no-data">No routes found.</li>}
                {routes.map((r, i) => {
                    const last = r.segments[r.segments.length - 1];
                    const destLoc = locs.find(l => l.locationCode === last.destinationCode);
                    const destLabel = `${destLoc?.name || last.destinationCode} (${last.destinationCode})`;
                    return (
                        <li
                            key={i}
                            className={i === selectedIndex ? 'active' : ''}
                            onClick={() => setSelectedIndex(i)}
                        >
                            {getViaLabel(r)} → {destLabel}
                        </li>
                    );
                })}
            </ul>

            {selectedIndex != null && (
                <RouteSidePanel
                    stops={[
                        routes[selectedIndex].segments[0].originCode,
                        ...routes[selectedIndex].segments.map(s => s.destinationCode),
                    ]}
                    types={routes[selectedIndex].segments.map(s => s.transportationType)}
                    days={routes[selectedIndex].segments.map(s => s.operatingDays)}
                    locs={locs}
                    onClose={() => setSelectedIndex(null)}
                />
            )}
        </div>
    );
}
