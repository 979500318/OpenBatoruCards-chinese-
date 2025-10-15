package open.batoru.data.cards;

import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ModifiableVariable.ModifiableValueReference;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.PieceAbility;

public final class PIECE_X_UNKNOWN extends Card {

    public PIECE_X_UNKNOWN()
    {
        setImageSets("WXDi-P13-004A");
        setDoubleFacedCardPairImageSetHint("WXDi-P13-004B");

        setOriginalName("UNKNOWN");
        setAltNames("アンノウン Announ");
        setDescription("jp",
                "チェックゾーンにあるこのカードを裏返して《UNKNOWN-CODE-RU-》を場に出す。この方法で場に出た《UNKNOWN-CODE-RU-》が場を離れる場合、代わりにゲームから除外される。"
        );

        setName("en", "Unknown");
        setDescription("en",
                "Turn this card face down in the Check Zone and put the \"UNKNOWN - CODE - RU -\" onto your field. If the \"UNKNOWN - CODE - RU -\" put onto the field this way would leave the field, instead it is removed from the game."
        );
        
        setName("en_fan", "UNKNOWN");
        setDescription("en_fan",
                "Flip this card in the check zone and put it onto the field as \"UNKNOWN-CODE-RU-\". If \"UNKNOWN-CODE-RU-\" put onto the field this way would leave the field, exclude it from the game instead."
        );

		setName("zh_simplified", "UNKNOWN");
        setDescription("zh_simplified", 
                "检查区的这张牌翻面把《ＵＮＫＮＯＷＮ－ＣＯＤＥ－ＲＵ－》出场。这个方法出场的《ＵＮＫＮＯＷＮ－ＣＯＤＥ－ＲＵ－》离场的场合，作为替代，从游戏除外。\n"
        );

        setCardFlags(CardFlag.DISSONA);

        setType(CardType.PIECE);
        setUseTiming(UseTiming.MAIN);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            PieceAbility piece = registerPieceAbility(this::onPieceEff);
            piece.setCondition(this::onPieceEffCond);
        }
        
        private ConditionState onPieceEffCond()
        {
            return getLRIG(getOwner()).getIndexedInstance().getLevel().getValue() >= 3 ? ConditionState.OK : ConditionState.WARN;
        }
        private void onPieceEff()
        {
            if(getCardIndex().getLocation() == CardLocation.CHECK_ZONE)
            {
                Card.IndexedInstance indexedInstance = transform(getCardIndex(), "WXDi-P13-004B", ChronoDuration.permanent());
                if(indexedInstance != null)
                {
                    ModifiableValueReference<Integer> value = indexedInstance.getCardStateFlags().addValue(CardStateFlag.DONT_RESET_STATS);
                    if(putOnField(getCardIndex()))
                    {
                        indexedInstance.getCardStateFlags().addValue(CardStateFlag.IS_CRAFT);
                    }
                    indexedInstance.getCardStateFlags().removeValue(value);
                }
            }
        }
    }
}
