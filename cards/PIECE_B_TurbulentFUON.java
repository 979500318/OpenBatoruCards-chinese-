package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.DataTable;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.PieceAbility;

public final class PIECE_B_TurbulentFUON extends Card {

    public PIECE_B_TurbulentFUON()
    {
        setImageSets("WXDi-P13-001");

        setOriginalName("不穏☆FU☆ON!");
        setAltNames("フオンフオン Fuon Fuon");
        setDescription("jp",
                "=U =E 合計３種類以上の色を持つ\n\n" +
                "カードを２枚引き、あなたの手札からシグニを３枚まで場に出す。その後、あなたの場に#Sのシグニが３体ある場合、対戦相手のシグニ１体を対象とし、それをデッキの一番下に置き、対戦相手は手札を２枚捨てる。"
        );

        setName("en", "Unrest☆UN☆REST!");
        setDescription("en",
                "=U =E You have the three LRIG on your field with three or more different colors among all members.\n\nDraw two cards and put up to three SIGNI from your hand onto your field. Then, if there are three #S SIGNI on your field, put target SIGNI on your opponent's field on the bottom of its owner's deck and your opponent discards two cards."
        );
        
        setName("en_fan", "Turbulent☆FU☆ON!");
        setDescription("en_fan",
                "=U =E with 3 or more colors among them\n\n" +
                "Draw 2 cards, and put up to 3 SIGNI from your hand onto the field. If there are 3 #S @[Dissona]@ SIGNI on your field, target 1 of your opponent's SIGNI, and put it on the bottom of their deck, and your opponent discards 2 cards from their hand."
        );

		setName("zh_simplified", "不稳☆FU☆ON！");
        setDescription("zh_simplified", 
                "=U=E持有合计3种类以上的颜色（你的场上的分身3只把这个条件满足）\n" +
                "抽2张牌，从你的手牌把精灵3张最多出场。然后，你的场上的#S的精灵在3只的场合，对战对手的精灵1只作为对象，将其放置到牌组最下面，对战对手把手牌2张舍弃。\n"
        );

        setCardFlags(CardFlag.DISSONA);

        setType(CardType.PIECE);
        setLRIGTeam(CardLRIGTeam.DREAM_TEAM);
        setColor(CardColor.BLUE);
        setCost(Cost.color(CardColor.BLUE, 1));
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
            return getColorsCount(getLRIGs(getOwner())) >= 3 ? ConditionState.OK : ConditionState.BAD;
        }
        private void onPieceEff()
        {
            draw(2);
            DataTable<CardIndex> data = playerTargetCard(0,3, new TargetFilter(TargetHint.FIELD).own().SIGNI().fromHand().playable());
            putOnField(data);
            
            if(new TargetFilter().own().SIGNI().dissona().getValidTargetsCount() == 3)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).OP().SIGNI()).get();
                returnToDeck(target, DeckPosition.BOTTOM);
                
                discard(getOpponent(), 2);
            }
        }
    }
}
