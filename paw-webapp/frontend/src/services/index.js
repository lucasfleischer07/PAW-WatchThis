import {CommentApi} from "../api/CommentApi";
import {ContentApi} from "../api/ContentApi";
import {ReviewApi} from "../api/ReviewApi";
import {UserApi} from "../api/UserApi";

const commentService = new CommentApi();
const contentService = new ContentApi();
const reviewService = new ReviewApi();
const userService = new UserApi();

export {
    commentService,
    contentService,
    reviewService,
    userService,
};