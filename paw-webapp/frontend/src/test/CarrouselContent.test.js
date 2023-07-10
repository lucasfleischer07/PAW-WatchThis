import * as React from 'react';
import {render, fireEvent, waitFor, getByRole, getByTestId, queryByTestId} from '@testing-library/react';
import { BrowserRouter as Router } from 'react-router-dom';
import CarrouselContent from "../views/components/CarrouselContent";

// Import the service mocks
import {
    listsService
} from '../services';


// Mock the context value
const mockAuthContextValue = {
    isLogged: jest.fn(), // Create a jest mock function
};
jest.mock('../services', () => ({
    listsService: {
        addUserWatchList: jest.fn(),
        deleteUserWatchList: jest.fn(),
    },
}));

describe('ContentCard', () => {
        beforeEach(() => {
            jest.clearAllMocks();
        });


        it('should add content to watchlist when movie is not in watchlist', async () => {
            // Mock the response from the listsService
            listsService.addUserWatchList.mockResolvedValue({ error: false });
            mockAuthContextValue.isLogged.mockReturnValue(true);

            const container = document.createElement('div'); // Create a valid container element
            render((
                <Router>
                    <CarrouselContent
                        user={"test"}
                        isInWatchList={false}
                        contentId={1}
                        contentName={"name"}
                        contentReleased={"2000"}
                        reviewsAmount={5}
                        contentRating={2}
                        contentType={"movie"}
                        contentImage={"img"}
                        contentGenre={"action"}
                        contentCreator={"yo"}
                    /></Router>
            ), { container });
            const removeButton = queryByTestId(container,'remove');
            expect(removeButton).toBeNull()
            const addButton = getByTestId(container,'add');
            fireEvent.submit(addButton);

            // Wait for the asynchronous code in handleAddToWatchlist to complete
            // Assert that the appropriate functions were called and the state is updated correctly
            expect(listsService.addUserWatchList).toHaveBeenCalled();
        });

        it('should remove content from watchlist when movie is in watchlist', async () => {
            // Mock the response from the listsService
            listsService.deleteUserWatchList.mockResolvedValue({ error: false });
            mockAuthContextValue.isLogged.mockReturnValue(true);

            const container = document.createElement('div'); // Create a valid container element
            render((
                <Router>
                    <AuthContext.Provider value={mockAuthContextValue}>
                    <CarrouselContent
                        user={"test"}
                        isInWatchList={true}
                        contentId={1}
                        contentName={"name"}
                        contentReleased={"2000"}
                        reviewsAmount={5}
                        contentRating={2}
                        contentType={"movie"}
                        contentImage={"img"}
                        contentGenre={"action"}
                        contentCreator={"yo"}
                    />
                    </AuthContext.Provider>
                    </Router>
            ), { container });
            const addButton = queryByTestId(container,'add');
            expect(addButton).toBeNull()
            const removeButton = getByTestId(container,'remove');
            fireEvent.submit(removeButton);

            // Wait for the asynchronous code in handleAddToWatchlist to complete
            // Assert that the appropriate functions were called and the state is updated correctly
            expect(listsService.deleteUserWatchList).toHaveBeenCalled();
        });
    }
)