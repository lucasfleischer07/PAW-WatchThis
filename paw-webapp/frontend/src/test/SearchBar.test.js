import React from 'react';
import { render, screen, fireEvent } from '@testing-library/react';
import { BrowserRouter } from 'react-router-dom';
import Header from '../views/components/Header'; // Update the path accordingly
import { AuthContext } from '../context/AuthContext';
import '@testing-library/jest-dom';

// Mock the AuthContext
const mockSignOut = jest.fn();
const mockAuthContextValue = {
    signOut: mockSignOut,
    // Add other values that your context provides
};

// Create a custom render function that includes necessary contexts
const renderWithRouterAndContext = (ui, { route = '/' } = {}) => {
    window.history.pushState({}, 'Test page', route);

    return render(
        <BrowserRouter>
            <AuthContext.Provider value={mockAuthContextValue}>
                {ui}
            </AuthContext.Provider>
        </BrowserRouter>
    );
};

describe('Header Component', () => {
    it('renders the search bar', () => {
        renderWithRouterAndContext(<Header />);
        const searchBar = screen.getByPlaceholderText('SearchMessage'); // Adjust if your search bar has a different placeholder
        expect(searchBar).toBeInTheDocument();
    });

    it('allows input in the search bar', () => {
        renderWithRouterAndContext(<Header />);
        const searchBar = screen.getByPlaceholderText('SearchMessage');
        fireEvent.change(searchBar, { target: { value: 'test query' } });
        expect(searchBar.value).toBe('test query');
    });
});
