import {CommentApi} from "../api/CommentApi";
import {ContentApi} from "../api/ContentApi";
import {ListsApi} from "../api/ListsApi";
import {ReportsApi} from "../api/ReportsApi";
import {ReviewApi} from "../api/ReviewApi";
import {UserApi} from "../api/UserApi";

const commentService = new CommentApi();
const contentService = new ContentApi();
const listsService = new ListsApi();
const reportsService = new ReportsApi();
const reviewService = new ReviewApi();
const userService = new UserApi();

export {
    commentService,
    contentService,
    listsService,
    reportsService,
    reviewService,
    userService,
};