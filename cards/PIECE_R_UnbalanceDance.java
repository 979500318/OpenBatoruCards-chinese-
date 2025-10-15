package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.data.DataTable;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.CardAbilities;
import open.batoru.data.ability.PieceAbility;

public final class PIECE_R_UnbalanceDance extends Card {

    public PIECE_R_UnbalanceDance()
    {
        setImageSets("WXDi-P12-002");

        setOriginalName("アンバランス・ダンス");
        setAltNames("アンバランスダンス Anbaransu Dansu");
        setDescription("jp",
                "=U =E 合計３種類以上の色を持つ\n\n" +
                "あなたの場に#Sのシグニが３体ある場合、対戦相手のすべてのシグニをバニッシュし、その後、対戦相手のエナゾーンから対戦相手のセンタールリグと共通する色を持たないカードを２枚まで対象とし、それらをトラッシュに置く。"
        );

        setName("en", "Unbalanced Dance");
        setDescription("en",
                "=U =E You have the three LRIG on your field with three or more different colors among all members.\n\nIf there are three #S SIGNI on your field, vanish all SIGNI on your opponent's field, then, put up to two target cards from your opponent's Ener Zone that do not share a color with your opponent's Center LRIG into their trash."
        );
        
        setName("en_fan", "Unbalance Dance");
        setDescription("en_fan",
                "=U =E with 3 or more colors among them\n\n" +
                "If there are 3 #S @[Dissona]@ SIGNI on your field, banish all of your opponent's SIGNI. Then, target up to 2 cards from your opponent's ener zone that don't share a common color with your opponent's center LRIG, and put them into the trash."
        );

		setName("zh_simplified", "失衡·狂舞");
        setDescription("zh_simplified", 
                "=U=E持有合计3种类以上的颜色（你的场上的分身3只把这个条件满足）\n" +
                "你的场上的#S的精灵在3只的场合，对战对手的全部的精灵破坏，然后，从对战对手的能量区把不持有与对战对手的核心分身共通颜色的牌2张最多作为对象，将这些放置到废弃区。\n"
        );

        setCardFlags(CardFlag.DISSONA);

        setType(CardType.PIECE);
        setLRIGTeam(CardLRIGTeam.DREAM_TEAM);
        setColor(CardColor.RED);
        setCost(Cost.color(CardColor.RED, 1) + Cost.colorless(1));
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
            if(CardAbilities.getColorsCount(getLRIGs(getOwner())) < 3) return ConditionState.BAD;
            return new TargetFilter().own().SIGNI().dissona().getValidTargetsCount() == 3 ? ConditionState.OK : ConditionState.WARN;
        }
        private void onPieceEff()
        {
            if(new TargetFilter().own().SIGNI().dissona().getValidTargetsCount() == 3)
            {
                banish(getSIGNIOnField(getOpponent()));

                DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.BURN).OP().not(new TargetFilter().withColor(getLRIG(getOpponent()).getIndexedInstance().getColor())).fromEner());
                trash(data);
            }
        }
    }
}
