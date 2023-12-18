import {fireEvent, getByTestId, getByText, render, waitFor} from '@testing-library/react';
import ReviewRegistrationPage from '../views/ReviewRegistrationPage';
import { AuthContext } from '../context/AuthContext';
import { BrowserRouter as Router } from 'react-router-dom';
import {contentService, reviewService} from '../services';
jest.mock('../services', () => ({
    reviewService: {
        reviewsCreation: jest.fn().mockResolvedValue({  }) // Mocking a resolved Promise
    },
    contentService: {
        getSpecificContent: jest.fn().mockResolvedValue({  }) // Mocking a resolved Promise
    }
}));
describe('ReviewRegistrationPage', () => {
    it('renders without crashing', () => {

        contentService.getSpecificContent.mockResolvedValueOnce({ });

        const mockAuthValue = { isLogged: () => true, user: { id: 1 } };
        render(
            <Router>
                <AuthContext.Provider value={mockAuthValue}>
                    <ReviewRegistrationPage />
                </AuthContext.Provider>
            </Router>
        );
    });
});
describe('ReviewRegistrationPage Form Submission', () => {

    it('submits the form with valid data', async () => {
        window.localStorage.setItem('user', JSON.stringify({ id: 1 }));
        const mockAuthValue = { isLogged: () => true, user: { id: 1 } };
        reviewService.reviewsCreation.mockResolvedValue({ error: null }); // Mock successful submission
        contentService.getSpecificContent.mockResolvedValueOnce({
            data: {
                contentPictureUrl: 'some-url',
                id:1
            }
        });

        const { getByPlaceholderText, getByTestId } = render(
            <Router>
                <AuthContext.Provider value={mockAuthValue}>
                    <ReviewRegistrationPage />
                </AuthContext.Provider>
            </Router>
        );

        const nameInput = getByPlaceholderText('Review.Mine');
        const description=getByTestId("description")
        const submitButton = getByTestId("submitButton");

        fireEvent.change(nameInput, { target: { value: 'Valid Name' } });
        fireEvent.change(description,{ target: { value: 'Valid Review wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww' } })
        fireEvent.click(submitButton);

        await waitFor(() => {
            expect(reviewService.reviewsCreation).toHaveBeenCalled(); // Check if the service was called
        });
    });
});

