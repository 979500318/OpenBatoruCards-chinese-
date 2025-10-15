package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;

public final class LRIG_W2_DefenderMCLION2nd extends Card {

    public LRIG_W2_DefenderMCLION2nd()
    {
        setImageSets("WXDi-P15-026");

        setOriginalName("防衛者MC.LION-2nd");
        setAltNames("ボウエイシャエムシーリオンセカンド Boueisha Emu Shii Rion Sekando");

        setName("en", "Defender MC LION - 2nd");
        
        
        setName("en_fan", "Defender MC.LION - 2nd");

		setName("zh_simplified", "防卫者MC.LION-2nd");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.LION);
        setLRIGTeam(CardLRIGTeam.CARD_JOCKEY);
        setColor(CardColor.WHITE);
        setCost(Cost.color(CardColor.WHITE, 1));
        setLevel(2);
        setLimit(5);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
}
