package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIG_W1_DefenderMCLION1st extends Card {

    public LRIG_W1_DefenderMCLION1st()
    {
        setImageSets("WXDi-P15-025");

        setOriginalName("防衛者MC.LION-1st");
        setAltNames("ボウエイシャエムシーリオンファースト Boueisha Emu Shii Rion Faasuto");

        setName("en", "Defender MC LION - 1st");
        
        
        setName("en_fan", "Defender MC.LION - 1st");

		setName("zh_simplified", "防卫者MC.LION-1st");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.LION);
        setLRIGTeam(CardLRIGTeam.CARD_JOCKEY);
        setColor(CardColor.WHITE);
        setLevel(1);
        setLimit(2);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
}
