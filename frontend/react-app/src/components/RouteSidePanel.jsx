import React from 'react';
import './RouteSidePanel.css';

export default function RouteSidePanel({ stops, types, days, locs, onClose }) {
    return (
        <div className="side-panel">
            <button className="close-btn" onClick={onClose}>
                Close
            </button>
            <ul className="segments-list">
                {stops.map((code, i) => {
                    const loc = locs.find(l => l.locationCode === code);
                    const name = loc?.name || code;
                    return (
                        <li key={i} className="segment">
                            <div className="circle" />
                            <div className="info">
                                <div className="loc">
                                    {name} ({code})
                                </div>
                                {i < types.length && (
                                    <>
                                        <div className="transport">{types[i].toLowerCase()}</div>
                                        <div className="days">
                                            Days: {days[i].join(',')}
                                        </div>
                                    </>
                                )}
                            </div>
                        </li>
                    );
                })}
            </ul>
        </div>
    );
}
