import axios from 'axios';

const api = axios.create({
    baseURL: '/api',
    headers: { 'Content-Type': 'application/json' },
});

export const locationService = {
    list:   () => api.get('/locations'),
    create: data => api.post('/locations', data),
    update: (id, data) => api.put(`/locations/${id}`, data),
    remove: id   => api.delete(`/locations/${id}`),
};

export const transportationService = {
    list:   () => api.get('/transportations'),
    create: data => api.post('/transportations', data),
    update: (id, data) => api.put(`/transportations/${id}`, data),
    remove: id => api.delete(`/transportations/${id}`),
};

export const routeService = {
    search: (origin, destination, date) =>
        api.get(`/routes?origin=${origin}&destination=${destination}&date=${date}`),
};

export default api;
