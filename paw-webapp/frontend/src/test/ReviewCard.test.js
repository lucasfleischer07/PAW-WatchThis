import * as React from 'react';
import {render, fireEvent, waitFor, getByRole, getByTestId, queryByTestId} from '@testing-library/react';
import { BrowserRouter as Router } from 'react-router-dom';
import ReviewCard from "../views/components/ReviewCard";
import Reputation from "../views/components/Reputation";
// Import the service mocks
import {
    reviewService,
    reportsService
} from '../services';
import {AuthContext} from "../context/AuthContext";

jest.mock('../views/components/Reputation', () => {
    return jest.fn().mockImplementation(() => <div data-testid="mock-reputation" />);
});
const mockAuthContextValue = {
    isLogged: jest.fn(), // Create a jest mock function
};
jest.mock('../services', () => ({
    reviewService:{
        deleteReview:jest.fn()
    },
    reportsService:{
        addReviewReport:jest.fn()
    }
}));
describe('ReviewCard', () => {
    beforeEach(() => {
        jest.clearAllMocks();
    });


    test('should delete a review', () => {
        mockAuthContextValue.isLogged.mockReturnValue(true);
        reviewService.deleteReview.mockResolvedValue({ error: false })
        const { getByTestId, getByText } = render(
            <Router>
                <AuthContext.Provider value={mockAuthContextValue}>
                    <ReviewCard />
                </AuthContext.Provider>
            </Router>
        );

        const deleteButton = getByTestId('delete');
        fireEvent.click(deleteButton);
        const confirmButton = getByText('Yes');
        fireEvent.click(confirmButton);
        expect(reviewService.deleteReview).toHaveBeenCalled()

    });

    test('should report', () => {
        mockAuthContextValue.isLogged.mockReturnValue(true);
        reportsService.addReviewReport.mockResolvedValue({ error: false })
        const { getByTestId, getByText } = render(
            <Router>
                <AuthContext.Provider value={mockAuthContextValue}>
                    <ReviewCard reviewId={1} reviewUser={"other"} userName={"me"} reviewUserId={2}/>
                </AuthContext.Provider>
            </Router>
        );

        const reportButton = getByTestId('report');
        fireEvent.click(reportButton);
        const spam = getByText('Report.Spam.Description');
        fireEvent.click(spam);
        const confirm = getByText('Yes');
        fireEvent.click(confirm);
        expect(reportsService.addReviewReport).toHaveBeenCalledWith(1,{"reportType": "Spam"})

    });
})