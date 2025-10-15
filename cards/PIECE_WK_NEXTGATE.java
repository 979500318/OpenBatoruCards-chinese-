package open.batoru.data.cards;

import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameLog;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.CardDataImageSet.Mask;
import open.batoru.data.ModifiableVariable.ModifiableValueReference;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.PieceAbility;

public final class PIECE_WK_NEXTGATE extends Card {

    public PIECE_WK_NEXTGATE()
    {
        setImageSets("WXDi-P16-001A", Mask.DOUBLE_FACED_UR+"WXDi-P16-001A");
        setDoubleFacedCardPairImageSetHint("WXDi-P16-001B");

        setOriginalName("NEXT GATE");
        setAltNames("ネクストゲート Nekusuto Geeto");
        setDescription("jp",
                "=U =E 白か黒のルリグを１体以上含む\n\n" +
                "このターンにあなたのセンタールリグがグロウしていない場合、チェックゾーンにあるこのカードを裏返し、あなたのセンタールリグはこの《扉の俯瞰者　ウトゥルス》にグロウコストを支払わずにグロウする。"
        );

        setName("en", "NEXT GATE");
        setDescription("en",
                "=U =E You have one or more white or black LRIG on your team.\n\nIf you haven't grown your Center LRIG this turn, turn this card face down in the Check Zone and your Center LRIG grows to this \"Ut'ulls, Gate Overseer\" without paying its Grow Cost."
        );
        
        setName("en_fan", "NEXT GATE");
        setDescription("en_fan",
                "=U =E with 1 or more being white or black\n\n" +
                "If your center LRIG didn't grow this turn, flip this card in your check zone, and your center LRIG grows as this \"\tUt'ulls, Door Overseer\" without paying its grow cost."
        );

		setName("zh_simplified", "NEXT GATE");
        setDescription("zh_simplified", 
                "=U=E含有白色或黑色的分身1只以上\n" +
                "这个回合你的核心分身没有成长的场合，检查区的这张牌翻面，你的核心分身不把这张《扉の俯瞰者　ウトゥルス》的成长费用支付，成长。\n"
        );

        setType(CardType.PIECE);
        setLRIGTeam(CardLRIGTeam.DREAM_TEAM);
        setColor(CardColor.WHITE, CardColor.BLACK);
        setUseTiming(UseTiming.MAIN);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
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
            return GameLog.getTurnRecordsCount(event -> event.getId() == GameEventId.GROW && event.getCaller().getLocation() == CardLocation.LRIG) == 0 && getLRIG(getOwner()).getIndexedInstance().getLevel().getValue() == 3 &&
                   getLRIG(getOwner()).getIndexedInstance().getLRIGType().matches(CardLRIGType.TAWIL, CardLRIGType.UMR) ? ConditionState.OK : ConditionState.WARN;
        }
        private void onPieceEff()
        {
            if(GameLog.getTurnRecordsCount(event -> event.getId() == GameEventId.GROW && event.getCaller().getLocation() == CardLocation.LRIG) == 0)
            {
                Card.IndexedInstance indexedInstance = transform(getCardIndex(), "WXDi-P16-001B", ChronoDuration.permanent());
                if(indexedInstance != null)
                {
                    ModifiableValueReference<Integer> value = indexedInstance.getCardStateFlags().addValue(CardStateFlag.DONT_RESET_STATS);
                    if(!grow(getCardIndex()))
                    {
                        exclude(getCardIndex());
                    }
                    indexedInstance.getCardStateFlags().removeValue(value);
                }
            }
        }
    }
}
