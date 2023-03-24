// import {useTranslation} from "react-i18next";
// import {useContext, useEffect, useState} from "react";
// import {dropDownStayGenreFilters} from "../../scripts/dropDownBehaviour";
// import {Link} from "react-router-dom";
//
//
// export default function Filters() {
//
//     const {t} = useTranslation()
//     const [genre, setGenre] = useState('');
//     const [durationFrom, setDurationFrom] = useState(undefined);
//     const [durationTo, setDurationTo] = useState(undefined);
//     const [sorting, setSorting] = useState(undefined);
//     const [pageType, setPageType] = useState('all');
//     const [query, setQuery] = useState(undefined);
//
//
//     const handleTypeChange = (type) => {
//         if (type === 'movie' || type === 'movies') {
//             setPageType('movies');
//         } else if (type === 'serie' || type === 'series') {
//             setPageType('series');
//         } else {
//             setPageType('all');
//         }
//     };
//
//     const handleGenreChange = (genre) => {
//         setGenre(genre);
//     };
//
//     let durationMessage = "";
//     if (param.durationTo === "1000") {
//         durationMessage = `Or more (${param.durationFrom})`;
//     } else if (param.durationFrom !== "" && param.durationFrom !== "ANY") {
//         durationMessage = `From ${param.durationFrom} to ${param.durationTo}`;
//     } else {
//         durationMessage = "Duration";
//     }
//
//     const handleDurationClick = (event) => {
//         const { durationFrom, durationTo } = event.currentTarget.dataset;
//         setSelectedDuration(`${durationFrom}_${durationTo}`);
//     };
//
//     return(
//         <div className="W-filter-div">
//             <div className="W-genre-duration-div">
//                 <div className="list-group">
//                     <div className="dropdown W-dropdown-button">
//                         {/*{param.type === 'movie' || param.type === 'movies' ? (*/}
//                         {/*    <sessionStorage.setItem('pageType', 'movies') />*/}
//                         {/*    ) : param.type === 'serie' || param.type === 'series' ? (*/}
//                         {/*    <sessionStorage.setItem('pageType', 'series') />*/}
//                         {/*    ) : (*/}
//                         {/*    <sessionStorage.setItem('pageType', 'all') />*/}
//                         {/*    )}*/}
//                         {genre !== '' && genre !== 'ANY' ? (
//                             <button id="genreGroupDrop" type="button" onClick={() => dropDownStayGenreFilters()} className="W-filter-title W-genre-filter btn dropdown-toggle" data-bs-toggle="dropdown" aria-expanded="false">
//                                 {genre}
//                             </button>
//                         ) : (
//                             <button id="genreGroupDrop" type="button" onClick={() => dropDownStayGenreFilters()} className="W-filter-title W-genre-filter btn dropdown-toggle" data-bs-toggle="dropdown" aria-expanded="false">
//                                 {t('GenreMessage')}
//                             </button>
//                         )}
//                         <ul className="dropdown-menu" id="drop1">
//                             {/*<c:url value={`/${pageType}/filters`} var="postPath">*/}
//                             {/*    { query !== undefined && query !== '' ? (*/}
//                             {/*        <c:param name="query" value={query} />*/}
//                             {/*    ) : null}*/}
//                             {/*    {durationFrom !== 'ANY' && durationFrom !== undefined ? (*/}
//                             {/*        <>*/}
//                             {/*            <c:param name="durationFrom" value={durationFrom} />*/}
//                             {/*            <c:param name="durationTo" value={durationTo} />*/}
//                             {/*        </>*/}
//                             {/*    ) : null}*/}
//                             {/*    {sorting !== 'ANY' && sorting != null ? (*/}
//                             {/*        <c:param name="sorting" value={sorting} />*/}
//                             {/*    ) : null}*/}
//                             {/*</c:url>*/}
//                             <form onSubmit={handleSubmit} encType="multipart/form-data" method="post">
//                                 <li className="mb-1 px-2">
//                                     <label>
//                                         <input type="checkbox" className="px-2" value="Action" onChange={() => handleGenreChange('Action')}/>
//                                         {' '} {t('Genre.Action')}
//                                     </label>
//                                 </li>
//                                 <li className="mb-1 px-2">
//                                     <label>
//                                         <input type="checkbox" className="px-2" value="Sci-Fi" onChange={() => handleGenreChange('Sci-Fi')}/>
//                                         {' '} {t('Genre.Science')}
//                                     </label>
//                                 </li>
//                                 <li className="mb-1 px-2">
//                                     <label>
//                                         <input type="checkbox" className="px-2" value="Comedy" onChange={() => handleGenreChange('Comedy')}/>
//                                         {' '} {t('Genre.Comedy')}
//                                     </label>
//                                 </li>
//                                 <li className="mb-1 px-2">
//                                     <label>
//                                         <input type="checkbox" className="px-2" value="Adventure" onChange={() => handleGenreChange('Adventure')}/>
//                                         {' '} {t('Genre.Adventure')}
//                                     </label>
//                                 </li>
//                                 <li className="mb-1 px-2">
//                                     <label>
//                                         <input type="checkbox" className="px-2" value="Drama" onChange={() => handleGenreChange('Drama')}/>
//                                         {' '} {t('Genre.Drama')}
//                                     </label>
//                                 </li>
//                                 <li className="mb-1 px-2">
//                                     <label>
//                                         <input type="checkbox" className="px-2" value="Horror" onChange={() => handleGenreChange('Horror')}/>
//                                         {' '} {t('Genre.Horror')}
//                                     </label>
//                                 </li>
//                                 <li className="mb-1 px-2">
//                                     <label>
//                                         <input type="checkbox" className="px-2" value="Animation" onChange={() => handleGenreChange('Animation')}/>
//                                         {' '} {t('Genre.Animation')}
//                                     </label>
//                                 </li>
//                                 <li className="mb-1 px-2">
//                                     <label>
//                                         <input type="checkbox" className="px-2" value="Thriller" onChange={() => handleGenreChange('Thriller')}/>
//                                         {' '} {t('Genre.Thriller')}
//                                     </label>
//                                 </li>
//                                 <li className="mb-1 px-2">
//                                     <label>
//                                         <input type="checkbox" className="px-2" value="Mystery" onChange={() => handleGenreChange('Mystery')}/>
//                                         {' '} {t('Genre.Mystery')}
//                                     </label>
//                                 </li>
//                                 <li className="mb-1 px-2">
//                                     <label>
//                                         <input type="checkbox" className="px-2" value="Crime" onChange={() => handleGenreChange('Crime')}/>
//                                         {' '} {t('Genre.Crime')}
//                                     </label>
//                                 </li>
//                                 <li className="mb-1 px-2">
//                                     <label>
//                                         <input type="checkbox" className="px-2" value="Fantasy" onChange={() => handleGenreChange('Fantasy')}/>
//                                         {' '} {t('Genre.Fantasy')}
//                                     </label>
//                                 </li>
//                                 <li className="mb-1 px-2">
//                                     <label>
//                                         <input type="checkbox" className="px-2" value="Romance" onChange={() => handleGenreChange('Romance')}/>
//                                         {' '} {t('Genre.Romance')}
//                                     </label>
//                                 </li>
//
//                                 <div className="W-apply-button">
//                                     <button type="submit" className="btn btn-danger mb-1 px-2">
//                                         {t('Apply')}
//                                     </button>
//                                 </div>
//                             </form>
//                         </ul>
//                     </div>
//                 </div>
//
//                 <div className="list-group">
//                     <div className="dropdown W-dropdown-button">
//                         <button id="genreGroupDrop" type="button" className="W-filter-title btn dropdown-toggle" data-bs-toggle="dropdown" aria-expanded="false">
//                             {durationMessage}
//                         </button>
//                         <ul className="dropdown-menu">
//                             <li>
//                                 {/*estos no sabemos si son links ni lo de href*/}
//                                 <Link
//                                     className="dropdown-item"
//                                     href={`/filters?pageTpye=${pageTpye}&genre=${param.genre}&sorting=${param.sorting}&query=${param.query}`}
//                                     onClick={() => showDuration(this)}
//                                 >
//                                     <spring:message code="Duration.Clear" />
//                                 </Link>
//                             </li>
//                             <li>
//                                 <Link
//                                     className="dropdown-item"
//                                     href={`/filters?pageTpye=${pageTpye}&genre=${param.genre}&sorting=${param.sorting}&query=${param.query}&durationFrom=0&durationTo=90`}
//                                     onClick={() => handleDropdownClick(0, 90)}
//                                 >
//                                     {t('Duration.90_90')}
//                                 </Link>
//                             </li>
//                             <li>
//                                 <Link
//                                     className="dropdown-item"
//                                     href={`/filters?pageTpye=${pageTpye}&genre=${param.genre}&sorting=${param.sorting}&query=${param.query}&durationFrom=90&durationTo=120`}
//                                     onClick={() => handleDropdownClick(90, 120)}
//                                 >
//                                     {t('Duration.90_120')}
//                                 </Link>
//                             </li>
//                             <li>
//                                 <Link
//                                     className="dropdown-item"
//                                     href={`/filters?pageTpye=${pageTpye}&genre=${param.genre}&sorting=${param.sorting}&query=${param.query}&durationFrom=120&durationTo=150`}
//                                     onClick={() => handleDropdownClick(120, 150)}
//                                 >
//                                     {t('Duration.120_150')}
//
//                                 </Link>
//                             </li>
//                             <li>
//                                 <Link
//                                     className="dropdown-item"
//                                     href={`/filters?pageTpye=${pageTpye}&genre=${param.genre}&sorting=${param.sorting}&query=${param.query}&durationFrom=150&durationTo=180`}
//                                     onClick={() => handleDropdownClick(150, 180)}
//                                 >
//                                     {t('Duration.150_180')}
//
//                                 </Link>
//                             </li>
//                         </ul>
//                     </div>
//                 </div>
//
//                 <div className="list-group">
//                     <div className="dropdown W-dropdown-button-sorting">
//                         {/*aca va lo de sorting pero mismo problema*/}
//                     </div>
//                 </div>
//             </div>
//         </div>
//     )
// }