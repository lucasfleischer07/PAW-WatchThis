import * as React from 'react';
import {render, fireEvent, screen, getByTestId, waitFor} from '@testing-library/react';
import { BrowserRouter as Router } from 'react-router-dom';
import Reputation from '../views/components/Reputation';
import { AuthContext } from '../context/AuthContext';
import {useContext} from "react";

import '@testing-library/jest-dom';
// Import the service mocks
import {
    commentService,
    reviewService,
} from '../services';

// Mock the context value
const mockAuthContextValue = {
    isLogged: jest.fn(), // Create a jest mock function
};

// Mock the services
jest.mock('../services'); // Update the path to match the location of your service mocks

describe('Reputation', () => {
    beforeEach(() => {
        jest.clearAllMocks();
    });
    const reviewComments=Promise.resolve(
        {
            error: false,
            data: [
                {
                    commentId: 3,
                    commentReportersUrl: 'http://localhost:8080/webapp_war/api/reports/comment/3',
                    text: 'Buenaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa',
                    user: {
                        email: 'carlostotoni@gmail.com',
                        id: 85,
                        image: 'http://localhost:8080/webapp_war/api/users/85/profileImage',
                        reputation: 4,
                        role: 'admin',
                        userReviews: 'http://localhost:8080/webapp_war/api/users/85/reviews',
                        userViewedListURL: 'http://localhost:8080/webapp_war/api/lists/viewedList/85',
                        userWatchListURL: 'http://localhost:8080/webapp_war/api/lists/watchList/85',
                        username: 'Carlos',
                    },
                },
            ],
            totalPages: 1,
        }
    )

    const mockReviewId = 1;
    const mockCommentForm = { comment: 'Test comment' };
    const initialReputation=1
    const userId=undefined
    test('should add a comment', async () => {
        // Mock the response of commentService.createComment
        commentService.getReviewComments = jest.fn().mockReturnValueOnce(reviewComments);
        commentService.createComment.mockResolvedValueOnce({ error: false, data: { } }); // Include new comment data in the response

        mockAuthContextValue.isLogged.mockReturnValue(true);

        render(
            <Router>
                <AuthContext.Provider value={mockAuthContextValue}>
                    <Reputation reviewId={mockReviewId} canComment={true} />
                </AuthContext.Provider>
            </Router>
        );

        await waitFor(() => {
            fireEvent.click(screen.getByText('Comment.Here'));
            fireEvent.change(screen.getByPlaceholderText('Comment.Placeholder'), { target: { value: mockCommentForm.comment } });
            fireEvent.click(screen.getByText('Comment.Title'));
        });

        // Verify that the comment was added
        expect(commentService.createComment).toHaveBeenCalledWith(useContext(AuthContext),mockReviewId,userId, mockCommentForm);

        // Check if the new comment is visible
        await waitFor(() => {
            expect(screen.getByText(mockCommentForm.comment)).toBeInTheDocument();
        });
    });


    test('should handle like', async () => {
        commentService.getReviewComments = jest.fn().mockReturnValueOnce(reviewComments);
        reviewService.reviewThumbUp.mockResolvedValueOnce({ error: false });
        mockAuthContextValue.isLogged.mockReturnValue(true);

        render(
            <Router>
                <AuthContext.Provider value={mockAuthContextValue}>
                    <Reputation reviewId={mockReviewId} reviewReputation={initialReputation} canComment={true} />
                </AuthContext.Provider>
            </Router>
        );

        // Check initial state of reviewReputation
        expect(screen.getByTestId(`rating${mockReviewId}`).textContent).toBe(String(initialReputation));

        // Fire the like event
        fireEvent.click(document.getElementById("likeButton1"));

        // Wait for state to update
        await waitFor(() => {
            // Check if the reviewReputation counter has been updated
            expect(reviewService.reviewThumbUp).toHaveBeenCalled();
            const expectedNewReputation = initialReputation + 1; // or the expected change
            expect(screen.getByTestId(`rating${mockReviewId}`).textContent).toBe(String(expectedNewReputation));
        });
    });

    test('should handle dislike', async () => {
        reviewService.reviewThumbDown.mockResolvedValueOnce({ error: false });
        commentService.getReviewComments = jest.fn().mockReturnValueOnce(reviewComments);
        mockAuthContextValue.isLogged.mockReturnValue(true);

        render(
            <Router>
                <AuthContext.Provider value={mockAuthContextValue}>
                    <Reputation reviewId={mockReviewId} reviewReputation={initialReputation} canComment={true} />
                </AuthContext.Provider>
            </Router>
        );

        // Check initial state of reviewReputation
        expect(screen.getByTestId(`rating${mockReviewId}`).textContent).toBe(String(initialReputation));

        // Fire the dislike event
        fireEvent.click(document.getElementById("dislikeButton1"));

        // Wait for state to update
        await waitFor(() => {
            // Check if the reviewReputation counter has been updated
            expect(reviewService.reviewThumbDown).toHaveBeenCalled();
            const expectedNewReputation = initialReputation - 1; // or the expected change
            expect(screen.getByTestId(`rating${mockReviewId}`).textContent).toBe(String(expectedNewReputation));
        });
    });
    ;
})
