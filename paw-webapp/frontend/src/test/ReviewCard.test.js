import * as React from 'react';
import {render, fireEvent, waitFor, getByRole, getByTestId, queryByTestId} from '@testing-library/react';
import { BrowserRouter as Router } from 'react-router-dom';
import ReviewCard from "../views/components/ReviewCard";
import Reputation from "../views/components/Reputation";
import {AuthContext} from "../context/AuthContext";
import {useContext} from "react";

// Import the service mocks
import {
    reviewService,
    reportsService, contentService
} from '../services';


jest.mock('../views/components/Reputation', () => {
    return jest.fn().mockImplementation(() => <div data-testid="mock-reputation" />);
});
const mockAuthContextValue = {
    isLogged: jest.fn(), // Create a jest mock function
};
jest.mock('../services', () => ({
    reviewService:{
        deleteReview:jest.fn(),
        addReviewReport:jest.fn()
    },
    contentService:{
        getSpecificContent:jest.fn()
    }
}));
describe('ReviewCard', () => {
    beforeEach(() => {
        jest.clearAllMocks();
    });


    test('should delete a review', () => {
        mockAuthContextValue.isLogged.mockReturnValue(true);
        reviewService.deleteReview.mockResolvedValue({ error: false })
        contentService.getSpecificContent.mockResolvedValue({ error: false })

        const { getByTestId, getByText } = render(
            <Router>
                <AuthContext.Provider value={mockAuthContextValue}>
                    <ReviewCard reviewId={1} reviewUser={"other"} userName={"me"} reviewUserId={2}/>
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
        reviewService.addReviewReport.mockResolvedValue({ error: false })
        contentService.getSpecificContent.mockResolvedValue({ error: false })

        const { getByTestId, getByText } = render(
            <Router>
                <AuthContext.Provider value={mockAuthContextValue}>
                    <ReviewCard reviewId={1} reviewUser={"other"} loggedUserName={"me"} reviewUserId={2}/>
                </AuthContext.Provider>
            </Router>
        );

        const reportButton = getByTestId('report');
        fireEvent.click(reportButton);
        const spam = getByText('Report.Spam.Description');
        fireEvent.click(spam);
        const confirm = getByText('Yes');
        fireEvent.click(confirm);
        expect(reviewService.addReviewReport).toHaveBeenCalledWith(mockAuthContextValue,1, undefined,{"reportType": "Spam"})

    });
})