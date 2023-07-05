import * as React from 'react';
import {render, fireEvent, screen, getByTestId, waitFor} from '@testing-library/react';
import { BrowserRouter as Router } from 'react-router-dom';
import Reputation from '../views/components/Reputation';
import { AuthContext } from '../context/AuthContext';

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

    test('should add a comment', async () => {
        // Mock the response of commentService.createComment
        commentService.getReviewComments=jest.fn().mockReturnValueOnce(reviewComments)
        commentService.createComment.mockResolvedValueOnce({ error: false });
        mockAuthContextValue.isLogged.mockReturnValue(true);
        render(
            <Router>
            <AuthContext.Provider value={mockAuthContextValue}>
                <Reputation reviewId={mockReviewId} canComment={true} />
            </AuthContext.Provider>
            </Router>
        );
        await waitFor(()=>{
            fireEvent.click(screen.getByText('Comment.Here'))
            fireEvent.change(screen.getByPlaceholderText('Comment.Placeholder'), { target: { value: mockCommentForm.comment } });
            fireEvent.click(screen.getByText('Comment.Title'));
        })


        // Verify that the comment was added
        expect(commentService.createComment).toHaveBeenCalledWith(mockReviewId, mockCommentForm);

    });


    test('should handle like', async () => {
        commentService.getReviewComments=jest.fn().mockReturnValueOnce(reviewComments)
        // Mock the response of reviewService.reviewThumbUp
        reviewService.reviewThumbUp.mockResolvedValueOnce({ error: false });
        mockAuthContextValue.isLogged.mockReturnValue(true);
        render(
            <Router>
                <AuthContext.Provider value={mockAuthContextValue}>
                    <Reputation reviewId={mockReviewId} canComment={true} />
                </AuthContext.Provider>
            </Router>
        );
        await waitFor(()=>{
            fireEvent.click(document.getElementById("likeButton1"))
            expect(reviewService.reviewThumbUp).toHaveBeenCalled();
        })

    });

    test('should handle dislike', async () => {
        // Mock the response of reviewService.reviewThumbDown
        reviewService.reviewThumbDown.mockResolvedValueOnce({ error: false });
        commentService.getReviewComments=jest.fn().mockReturnValueOnce(reviewComments)
        // Mock the response of reviewService.reviewThumbUp
        mockAuthContextValue.isLogged.mockReturnValue(true);

        const { container }=render(
            <Router>
                <AuthContext.Provider value={mockAuthContextValue}>
                    <Reputation reviewId={mockReviewId} reviewReputation={1} isLike={true} isDislike={false} canComment={true} />
                </AuthContext.Provider>
            </Router>
        );


        await waitFor(()=>{
            fireEvent.click(document.getElementById("dislikeButton1"))
            expect(reviewService.reviewThumbDown).toHaveBeenCalled();
        })



});})
