import {fireEvent, getByTestId, getByText, render, waitFor} from '@testing-library/react';
import ReviewEditionPage from '../views/ReviewEditionPage';
import { AuthContext } from '../context/AuthContext';
import { BrowserRouter as Router } from 'react-router-dom';
import {contentService, reviewService} from '../services';
jest.mock('../services', () => ({
    reviewService: {
        reviewEdition: jest.fn().mockResolvedValue({  }), // Mocking a resolved Promise
        getSpecificReview: jest.fn().mockResolvedValue({  }) // Mocking a resolved Promise
    },
    contentService: {
        getSpecificContent: jest.fn().mockResolvedValue({  }) // Mocking a resolved Promise
    }
}));
describe('ReviewRegistrationPage', () => {
    it('renders without crashing', () => {

        contentService.getSpecificContent.mockResolvedValueOnce({ });
        reviewService.getSpecificReview.mockResolvedValue({})

        const mockAuthValue = { isLogged: () => true, user: { id: 1 } };
        render(
            <Router>
                <AuthContext.Provider value={mockAuthValue}>
                    <ReviewEditionPage />
                </AuthContext.Provider>
            </Router>
        );
    });
});
describe('ReviewRegistrationPage Form Submission', () => {

    it('submits the form with valid data', async () => {
        window.localStorage.setItem('user', JSON.stringify({ id: 1 }));
        const mockAuthValue = { isLogged: () => true, user: { id: 1 } };
        reviewService.reviewEdition.mockResolvedValue({ error: null }); // Mock successful submission
        contentService.getSpecificContent.mockResolvedValueOnce({
            data: {
                contentPictureUrl: 'some-url',
                id:1
            }
        });
        reviewService.getSpecificReview.mockResolvedValue({})

        const { getByPlaceholderText, getByTestId } = render(
            <Router>
                <AuthContext.Provider value={mockAuthValue}>
                    <ReviewEditionPage />
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
            expect(reviewService.reviewEdition()).toHaveBeenCalled(); // Check if the service was called
        });
    });
});
