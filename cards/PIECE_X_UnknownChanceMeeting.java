package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameLog;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ModifiableVariable.ModifiableValueReference;
import open.batoru.data.DataTable;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.PieceAbility;

public final class PIECE_X_UnknownChanceMeeting extends Card {

    public PIECE_X_UnknownChanceMeeting()
    {
        setImageSets("WXDi-P13-003A");
        setDoubleFacedCardPairImageSetHint("WXDi-P13-003B");

        setOriginalName("未知の邂逅");
        setAltNames("ミチノカイコウ Michi no Kaikou");
        setDescription("jp",
                "=U =E 白か黒のルリグを１体以上含む\n\n" +
                "このターンにあなたがセンタールリグをグロウしていない場合、手札をすべて捨てあなたのエナゾーンからすべてのカードをトラッシュに置く。この方法でカードが５枚以上トラッシュに置かれた場合、チェックゾーンにあるこのカードを裏返し、あなたのセンタールリグはこの《未知の巫女　マユ》にグロウコストを支払わずにグロウする。"
        );

        setName("en", "Mysterious Rendezvous");
        setDescription("en",
                "=U =E You have one or more white or black LRIG on your team.\n\nIf you haven't grown your Center LRIG this turn, discard your hand and put all cards in your Ener Zone into your trash. If five or more cards were put into the trash this way, turn this card face down in the Check Zone and your Center LRIG grows to this \"Mayu, Unknown Miko\" without paying its Grow Cost."
        );
        
        setName("en_fan", "Unknown Chance Meeting");
        setDescription("en_fan",
                "=U =E with 1 or more being white or black\n\n" +
                "If your center LRIG didn't grow this turn, discard all cards from your hand and put all cards from your ener zone into the trash. If 5 or more cards were put into the trash this way, flip this card in your check zone, and your center LRIG grows as this \"Mayu, Unknown Miko\" without paying its grow cost."
        );

		setName("zh_simplified", "未知的邂逅");
        setDescription("zh_simplified", 
                "=U=E含有白色或黑色的分身1只以上\n" +
                "这个回合你的核心分身没有成长的场合，把手牌全部舍弃且从你的能量区把全部的牌放置到废弃区。这个方法把牌5张以上放置到废弃区的场合，检查区的这张牌翻面，你的核心分身不把这张《未知の巫女　マユ》的成长费用支付，成长。\n"
        );

        setCardFlags(CardFlag.DISSONA);

        setType(CardType.PIECE);
        setLRIGTeam(CardLRIGTeam.DREAM_TEAM);
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
            return GameLog.getTurnRecordsCount(event -> event.getId() == GameEventId.GROW && event.getCaller().getLocation() == CardLocation.LRIG) == 0 &&
                    getLRIG(getOwner()).getIndexedInstance().getLevel().getValue() == 3 &&
                    getLRIG(getOwner()).getIndexedInstance().getLRIGType().matches(CardLRIGType.TAMA, CardLRIGType.IONA) ? ConditionState.OK : ConditionState.WARN;
        }
        private void onPieceEff()
        {
            if(GameLog.getTurnRecordsCount(event -> event.getId() == GameEventId.GROW && event.getCaller().getLocation() == CardLocation.LRIG) == 0)
            {
                int sum = 0;
                DataTable<CardIndex> data = discard(getCardsInHand(getOwner()));
                if(data.get() != null) sum += data.size();
                sum += trash(getCardsInEner(getOwner()));
                
                if(sum >= 5)
                {
                    Card.IndexedInstance indexedInstance = transform(getCardIndex(), "WXDi-P13-003B", ChronoDuration.permanent());
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
}

