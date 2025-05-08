import React from 'react';
import { BrowserRouter, Routes, Route, Navigate, useLocation } from 'react-router-dom';
import Header from './components/Header';
import Sidebar from './components/Sidebar';
import Locations from './pages/Locations';
import Transportations from './pages/Transportations';
import RoutesPage from './pages/Routes';
import './App.css';

export default function App() {
  return (
      <BrowserRouter>
        <Main />
      </BrowserRouter>
  );
}

function Main() {
  const location = useLocation();

  const pathToTitle = {
    '/locations':       'Locations',
    '/transportations': 'Transportations',
    '/routes':          'Routes',
  };
  const pageName = pathToTitle[location.pathname] || 'Welcome';

  return (
      <div className="app">
        <Header pageName={pageName} />

        <Sidebar />
        <div className="content">
          <Routes>
            <Route path="/" element={<Navigate replace to="/locations" />} />
            <Route path="/locations"       element={<Locations />} />
            <Route path="/transportations" element={<Transportations />} />
            <Route path="/routes"          element={<RoutesPage />} />
          </Routes>
        </div>
      </div>
  );
}
