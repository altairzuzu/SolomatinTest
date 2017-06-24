package ru.fordexsys.solomatintest.data;//package ru.velogame.bikewithme.data;
//
//import android.accounts.Account;
//
//import java.util.List;
//
//import javax.inject.Inject;
//import javax.inject.Singleton;
//
//import ru.velogame.bikewithme.data.local.DatabaseHelper;
//import ru.velogame.bikewithme.data.local.PreferencesHelper;
//import ru.velogame.bikewithme.data.model.CheckIn;
//import ru.velogame.bikewithme.data.model.CheckInRequest;
//import ru.velogame.bikewithme.data.model.Encounter;
//import ru.velogame.bikewithme.data.model.RegisteredBeacon;
//import ru.velogame.bikewithme.data.model.Ribot;
//import ru.velogame.bikewithme.data.model.Venue;
//import ru.velogame.bikewithme.data.remote.VKApi;
//import ru.velogame.bikewithme.data.remote.GoogleAuthHelper;
//import ru.velogame.bikewithme.data.remote.VKApi.SignInRequest;
//import ru.velogame.bikewithme.data.remote.VKApi.SignInResponse;
//import ru.velogame.bikewithme.util.DateUtil;
//import ru.velogame.bikewithme.util.EventPosterHelper;
//import rx.Observable;
//import rx.functions.Action0;
//import rx.functions.Action1;
//import rx.functions.Func1;
//
//@Singleton
//public class DataManager {
//
//    private final VKApi mBikeWithMeService;
//    private final DatabaseHelper mDatabaseHelper;
//    private final PreferencesHelper mPreferencesHelper;
//    private final EventPosterHelper mEventPoster;
//
//    @Inject
//    public DataManager(VKApi bikeWithMeService,
//                       DatabaseHelper databaseHelper,
//                       PreferencesHelper preferencesHelper,
//                       EventPosterHelper eventPosterHelper,
//                       GoogleAuthHelper googleAuthHelper) {
//        mBikeWithMeService = bikeWithMeService;
//        mDatabaseHelper = databaseHelper;
//        mPreferencesHelper = preferencesHelper;
//        mEventPoster = eventPosterHelper;
//    }
//
//    public PreferencesHelper getPreferencesHelper() {
//        return mPreferencesHelper;
//    }
//
//    /**
//     * Sign in with a Google account.
//     * 1. Retrieve an google auth code for the given account
//     * 2. Sends code and account to API
//     * 3. If success, saves ribot profile and API access token in preferences
//     */
//    public Observable<Ribot> signIn(Account account) {
//        return mGoogleAuthHelper.retrieveAuthTokenAsObservable(account)
//                .concatMap(new Func1<String, Observable<SignInResponse>>() {
//                    @Override
//                    public Observable<SignInResponse> call(String googleAccessToken) {
//                        return mBikeWithMeService.signIn(new SignInRequest(googleAccessToken));
//                    }
//                })
//                .map(new Func1<SignInResponse, Ribot>() {
//                    @Override
//                    public Ribot call(SignInResponse signInResponse) {
//                        mPreferencesHelper.putAccessToken(signInResponse.accessToken);
//                        mPreferencesHelper.putSignedInRibot(signInResponse.ribot);
//                        return signInResponse.ribot;
//                    }
//                });
//    }
//
//    public Observable<Void> signOut() {
//        return mDatabaseHelper.clearTables()
//                .doOnCompleted(new Action0() {
//                    @Override
//                    public void call() {
//                        mPreferencesHelper.clear();
//                        mEventPoster.postEventSafely(new BusEvent.UserSignedOut());
//                    }
//                });
//    }
//
//    public Observable<List<Ribot>> getRibots() {
////        String auth = VKApi.Util.buildAuthorization(mPreferencesHelper.getAccessToken());
//        return mBikeWithMeService.getRibots(auth, "latestCheckIn");
//    }
//
//    /**
//     * Retrieve list of venues. Behaviour:
//     * 1. Return cached venues (empty list if none is cached)
//     * 2. Return API venues (if different to cached ones)
//     * 3. Save new venues from API in cache
//     * 5. If an error happens and cache is not empty, returns venues from cache.
//     */
//    public Observable<List<Venue>> getVenues() {
//        String auth = VKApi.Util.buildAuthorization(mPreferencesHelper.getAccessToken());
//        return mBikeWithMeService.getVenues(auth)
//                .doOnNext(new Action1<List<Venue>>() {
//                    @Override
//                    public void call(List<Venue> venues) {
//                        mPreferencesHelper.putVenues(venues);
//                    }
//                })
//                .onErrorResumeNext(new Func1<Throwable, Observable<? extends List<Venue>>>() {
//                    @Override
//                    public Observable<? extends List<Venue>> call(Throwable throwable) {
//                        return getVenuesRecoveryObservable(throwable);
//                    }
//                })
//                .startWith(mPreferencesHelper.getVenuesAsObservable())
//                .distinct();
//    }
//
//    // Returns venues from cache. If cache is empty, it forwards the error.
//    private Observable<List<Venue>> getVenuesRecoveryObservable(Throwable error) {
//        return mPreferencesHelper.getVenuesAsObservable()
//                .switchIfEmpty(Observable.<List<Venue>>error(error));
//    }
//
//    /**
//     * Performs a manual check in, either at a venue or a location.
//     * Use CheckInRequest.fromVenue() or CheckInRequest.fromLabel() to create the request.
//     * If the the check-in is successful, it's saved as the latest check-in.
//     */
//    public Observable<CheckIn> checkIn(CheckInRequest checkInRequest) {
//        String auth = VKApi.Util.buildAuthorization(mPreferencesHelper.getAccessToken());
//        return mBikeWithMeService.checkIn(auth, checkInRequest)
//                .doOnNext(new Action1<CheckIn>() {
//                    @Override
//                    public void call(CheckIn checkIn) {
//                        mPreferencesHelper.putLatestCheckIn(checkIn);
//                    }
//                });
//    }
//
//    /**
//     * Marks a previous check-in as "checkedOut" and updates the value in preferences
//     * if the check-in matches the latest check-in.
//     */
//    public Observable<CheckIn> checkOut(final String checkInId) {
//        String auth = VKApi.Util.buildAuthorization(mPreferencesHelper.getAccessToken());
//        return mBikeWithMeService.updateCheckIn(auth, checkInId,
//                new VKApi.UpdateCheckInRequest(true))
//                .doOnNext(new Action1<CheckIn>() {
//                    @Override
//                    public void call(CheckIn checkInUpdated) {
//                        CheckIn latestCheckIn = mPreferencesHelper.getLatestCheckIn();
//                        if (latestCheckIn != null && latestCheckIn.id.equals(checkInUpdated.id)) {
//                            mPreferencesHelper.putLatestCheckIn(checkInUpdated);
//                        }
//                        String encounterCheckInId =
//                                mPreferencesHelper.getLatestEncounterCheckInId();
//                        if (encounterCheckInId != null &&
//                                encounterCheckInId.equals(checkInUpdated.id)) {
//                            mPreferencesHelper.clearLatestEncounter();
//                        }
//                    }
//                });
//    }
//
//    /**
//     * Returns today's latest manual check in, if there is one.
//     */
//    public Observable<CheckIn> getTodayLatestCheckIn() {
//        return mPreferencesHelper.getLatestCheckInAsObservable()
//                .filter(new Func1<CheckIn, Boolean>() {
//                    @Override
//                    public Boolean call(CheckIn checkIn) {
//                        return DateUtil.isToday(checkIn.checkedInDate.getTime());
//                    }
//                });
//    }
//
//    public Observable<Encounter> performBeaconEncounter(String beaconId) {
//        String auth = VKApi.Util.buildAuthorization(mPreferencesHelper.getAccessToken());
//        return mBikeWithMeService.performBeaconEncounter(auth, beaconId)
//                .doOnNext(new Action1<Encounter>() {
//                    @Override
//                    public void call(Encounter encounter) {
//                        mPreferencesHelper.putLatestEncounter(encounter);
//                    }
//                });
//    }
//
//    public Observable<Encounter> performBeaconEncounter(String uuid, int major, int minor) {
//        Observable<RegisteredBeacon> errorObservable = Observable.error(
//                new BeaconNotRegisteredException(uuid, major, minor));
//        return mDatabaseHelper.findRegisteredBeacon(uuid, major, minor)
//                .switchIfEmpty(errorObservable)
//                .concatMap(new Func1<RegisteredBeacon, Observable<Encounter>>() {
//                    @Override
//                    public Observable<Encounter> call(RegisteredBeacon registeredBeacon) {
//                        return performBeaconEncounter(registeredBeacon.id);
//                    }
//                });
//    }
//
//    public Observable<String> findRegisteredBeaconsUuids() {
//        return mDatabaseHelper.findRegisteredBeaconsUuids();
//    }
//
//    public Observable<Void> syncRegisteredBeacons() {
//        String auth = VKApi.Util.buildAuthorization(mPreferencesHelper.getAccessToken());
//        return mBikeWithMeService.getRegisteredBeacons(auth)
//                .concatMap(new Func1<List<RegisteredBeacon>, Observable<Void>>() {
//                    @Override
//                    public Observable<Void> call(List<RegisteredBeacon> beacons) {
//                        return mDatabaseHelper.setRegisteredBeacons(beacons);
//                    }
//                })
//                .doOnCompleted(postEventSafelyAction(new BusEvent.BeaconsSyncCompleted()));
//    }
//
//    //  Helper method to post events from doOnCompleted.
//    private Action0 postEventSafelyAction(final Object event) {
//        return new Action0() {
//            @Override
//            public void call() {
//                mEventPoster.postEventSafely(event);
//            }
//        };
//    }
//
//
//}
