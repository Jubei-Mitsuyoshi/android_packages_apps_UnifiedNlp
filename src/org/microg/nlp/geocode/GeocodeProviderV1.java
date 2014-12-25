package org.microg.nlp.geocode;

import android.content.Context;
import android.location.Address;
import android.location.GeocoderParams;
import com.android.location.provider.GeocodeProvider;

import java.util.List;

class GeocodeProviderV1 extends GeocodeProvider implements org.microg.nlp.geocode.GeocodeProvider {
    private BackendFuser backendFuser;
    private Context context;

    public GeocodeProviderV1(Context context) {
        this.context = context;
        reload();
    }

    @Override
    public String onGetFromLocation(double latitude, double longitude, int maxResults,
            GeocoderParams params, List<Address> addresses) {
        List<Address> fuserResult = backendFuser
                .getFromLocation(latitude, longitude, maxResults, params.getLocale().toString());
        return handleResult(addresses, fuserResult);
    }

    @Override
    public String onGetFromLocationName(String locationName, double lowerLeftLatitude,
            double lowerLeftLongitude, double upperRightLatitude, double upperRightLongitude,
            int maxResults, GeocoderParams params, List<Address> addresses) {
        List<Address> fuserResult = backendFuser.getFromLocationName(locationName,
                lowerLeftLatitude, lowerLeftLongitude, upperRightLatitude, upperRightLongitude,
                maxResults, params.getLocale().toString());
        return handleResult(addresses, fuserResult);
    }

    private String handleResult(List<Address> realResult, List<Address> fuserResult) {
        if (fuserResult == null) {
            return "no backend";
        } else if (fuserResult.isEmpty()) {
            return "no result";
        } else {
            realResult.addAll(fuserResult);
            return null;
        }
    }

    @Override
    public void reload() {
        backendFuser = new BackendFuser(context);
    }
}
