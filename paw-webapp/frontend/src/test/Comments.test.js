import * as React from 'react';
import {render, fireEvent, waitFor, getByRole, getByTestId, queryByTestId} from '@testing-library/react';
import { BrowserRouter as Router } from 'react-router-dom';
import Comments from "../views/components/Comments";
import {useContext} from "react";
import { AuthContext } from '../context/AuthContext';

// Import the service mocks
import {
    commentService,
    reportsService, reviewService, userService
} from '../services';
import ReviewCard from "../views/components/ReviewCard";


const mockAuthContextValue = {
    isLogged: jest.fn(), // Create a jest mock function
};
jest.mock('../services', () => ({
    commentService:{
        commentDelete:jest.fn(),
        addCommentReport:jest.fn()

    },
    userService:{
        getUserInfo:jest.fn()
    }
}));

describe('Comments', () => {
    beforeEach(() => {
        jest.clearAllMocks();
    });


    test('should delete a comment', () => {
        mockAuthContextValue.isLogged.mockReturnValue(true);
        commentService.commentDelete.mockResolvedValue({ error: false })
        userService.getUserInfo.mockResolvedValue({ data: {
                id: 1, // or any other ID you want to use for testing
                username: 'me',
                image: "path/to/image.jpg" // or any other image path you want to use for testing
            },error: false })

        const { getByTestId, getByText } = render(
            <Router>
                <AuthContext.Provider value={mockAuthContextValue}>
                    <Comments  commentId={1} userCreatorUsername={''} loggedUserName={''} userCreatorId={1} loggedUserId={1}/>
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
        commentService.addCommentReport.mockResolvedValue({ error: false })
        userService.getUserInfo.mockResolvedValue({ error: false })
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
        expect(commentService.addCommentReport).toHaveBeenCalledWith(mockAuthContextValue,2, 1,{"reportType": "Spam"})

    });
})