import { NavLink } from 'react-router-dom';
import React from 'react';

export default function Sidebar() {
    return (
        <nav className="sidebar">
            <h3 style={{ padding: '16px' }}>Menu</h3>
            <NavLink to="/locations">Locations</NavLink>
            <NavLink to="/transportations">Transportations</NavLink>
            <NavLink to="/routes">Routes</NavLink>
        </nav>
    );
}
