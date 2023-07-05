import * as React from 'react';
import {render, fireEvent, waitFor, getByRole, getByTestId, queryByTestId} from '@testing-library/react';
import { BrowserRouter as Router } from 'react-router-dom';
import Comments from "../views/components/Comments";
import { AuthContext } from '../context/AuthContext';

// Import the service mocks
import {
    commentService,
    reportsService, reviewService
} from '../services';
import ReviewCard from "../views/components/ReviewCard";


const mockAuthContextValue = {
    isLogged: jest.fn(), // Create a jest mock function
};
jest.mock('../services', () => ({
    commentService:{
        commentDelete:jest.fn()
    },
    reportsService:{
        addCommentReport:jest.fn()
    }
}));

describe('Comments', () => {
    beforeEach(() => {
        jest.clearAllMocks();
    });


    test('should delete a review', () => {
        mockAuthContextValue.isLogged.mockReturnValue(true);
        commentService.commentDelete.mockResolvedValue({ error: false })
        const { getByTestId, getByText } = render(
            <Router>
                <AuthContext.Provider value={mockAuthContextValue}>
                    <Comments  commentId={1} userCreatorUsername={'me'} loggedUserName={'me'} userCreatorId={1} loggedUserId={1}/>
                </AuthContext.Provider>
            </Router>
        );

        const deleteButton = getByTestId('delete1');
        fireEvent.click(deleteButton);
        const confirmButton = getByText('Yes');
        fireEvent.click(confirmButton);
        expect(commentService.commentDelete).toHaveBeenCalled()

    });

    test('should report', () => {
        mockAuthContextValue.isLogged.mockReturnValue(true);
        reportsService.addCommentReport.mockResolvedValue({ error: false })
        const { getByTestId, getByText } = render(
            <Router>
                <AuthContext.Provider value={mockAuthContextValue}>
                    <Comments  commentId={1} userCreatorUsername={'me'} loggedUserName={'other'} userCreatorId={1} loggedUserId={2}/>
                </AuthContext.Provider>
            </Router>
        );

        const reportButton = getByTestId('report1');
        fireEvent.click(reportButton);
        const spam = getByText('Report.Spam.Description');
        fireEvent.click(spam);
        const confirm = getByText('Yes');
        fireEvent.click(confirm);
        expect(reportsService.addCommentReport).toHaveBeenCalledWith(1,{"reportType": "Spam"})

    });
})