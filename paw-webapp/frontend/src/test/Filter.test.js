import React from 'react';
import {render, screen, fireEvent, act} from '@testing-library/react';
import ContentPage from '../views/ContentPage';
import * as assert from "assert";
import {MemoryRouter, Route, Router, Routes} from "react-router-dom";
import { AuthContext } from '../context/AuthContext';


// Import the service mocks
import {
    contentService
} from '../services';


// Mock the context value
const mockAuthContextValue = {
    isLogged: jest.fn(), // Create a jest mock function
};
jest.mock('../services', () => ({
    contentService: {
        getContentByType:jest.fn(),
    },
}));
describe('ContentPage.js',()=>{
    beforeEach(() => {
        jest.clearAllMocks();
    });

    it('should filter content by genre', () => {
        mockAuthContextValue.isLogged.mockReturnValue(true);
        contentService.getContentByType.mockResolvedValue({ error: false });
        render(<MemoryRouter>
            <AuthContext.Provider value={mockAuthContextValue}>
                <ContentPage/>
            </AuthContext.Provider>
        </MemoryRouter>)
            ;

        const genreFilter = screen.getByText('GenreMessage');
        fireEvent.click(genreFilter)
        const actionButton=screen.getByText('Genre.Action');
        fireEvent.click(actionButton);
        const mysteryButton=screen.getByText('Genre.Mystery');
        fireEvent.click(mysteryButton);
        const applyButton=screen.getByText('Apply')
        fireEvent.click(applyButton)
        expect(contentService.getContentByType).toHaveBeenCalledWith(undefined,1,'Action,Mystery',"","","","")
    })
    it('should filter content by duration', () => {
        mockAuthContextValue.isLogged.mockReturnValue(true);
        contentService.getContentByType.mockResolvedValue({ error: false });
        // Render the component
        render(<MemoryRouter >
            <AuthContext.Provider value={mockAuthContextValue}>
                <ContentPage/>
            </AuthContext.Provider>
        </MemoryRouter>)
        ;

        const durationFilter = screen.getByText('DurationMessage');
        fireEvent.click(durationFilter);
        const durationButton=screen.getByText('Duration.0_90');
        fireEvent.click(durationButton)
        expect(contentService.getContentByType).toHaveBeenCalledWith(undefined,1,"","0","90","","")

    })
    it('should sort content', () => {
        mockAuthContextValue.isLogged.mockReturnValue(true);
        contentService.getContentByType.mockResolvedValue({ error: false });
        // Render the component
        render(<MemoryRouter >
            <AuthContext.Provider value={mockAuthContextValue}>
                <ContentPage/>
            </AuthContext.Provider>
        </MemoryRouter>)
        ;

        const sortFilter = screen.getByText('SortMessage');
        fireEvent.click(sortFilter);
        const sortButton=screen.getByText('Sort.OlderReleased');
        fireEvent.click(sortButton)
        expect(contentService.getContentByType).toHaveBeenCalledWith(undefined,1,"","","","OlderReleased","")

    })
    it('should filter and sort content', () => {
        mockAuthContextValue.isLogged.mockReturnValue(true);
        contentService.getContentByType.mockResolvedValue({ error: false });
        // Render the component
        render(<MemoryRouter >
            <AuthContext.Provider value={mockAuthContextValue}>
                <ContentPage/>
            </AuthContext.Provider>
        </MemoryRouter>)
        ;

        const sortFilter = screen.getByText('SortMessage'); // Replace with the actual label
        fireEvent.click(sortFilter);
        const sortButton=screen.getByText('Sort.OlderReleased');
        fireEvent.click(sortButton)
        const durationFilter = screen.getByText('DurationMessage'); // Replace with the actual label
        fireEvent.click(durationFilter);
        const durationButton=screen.getByText('Duration.0_90');
        fireEvent.click(durationButton)
        const genreFilter = screen.getByText('GenreMessage'); // Replace with the actual label
        fireEvent.click(genreFilter)
        const actionButton=screen.getByText('Genre.Action');
        fireEvent.click(actionButton);
        const mysteryButton=screen.getByText('Genre.Mystery');
        fireEvent.click(mysteryButton);
        const applyButton=screen.getByText('Apply')
        fireEvent.click(applyButton)
        expect(contentService.getContentByType).toHaveBeenCalledWith(undefined,1,'Action,Mystery',"0","90","OlderReleased","")

    })
})