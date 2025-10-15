package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.data.DataTable;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.CardAbilities;
import open.batoru.data.ability.PieceAbility;
import open.batoru.data.ability.stock.StockAbilitySLancer;

public final class PIECE_G_LANCERINTHEDARK extends Card {

    public PIECE_G_LANCERINTHEDARK()
    {
        setImageSets("WXDi-P12-003");

        setOriginalName("LANCER IN THE DARK");
        setAltNames("ランサーインザダーク Ransaa In Za Daaku");
        setDescription("jp",
                "=U =E 合計３種類以上の色を持つ\n\n" +
                "あなたのトラッシュにあるすべてのカードをデッキに加えてシャッフルし、【エナチャージ２】をする。\n" +
                "その後、あなたのエナゾーンから#Sのシグニを２枚まで対象とし、それらを場に出す。\n" +
                "その後、あなたの#Sのシグニ１体を対象とし、ターン終了時まで、それは【Ｓランサー】を得る。"
        );

        setName("en", "Lancer in the Dark");
        setDescription("en",
                "=U =E You have the three LRIG on your field with three or more different colors among all members.\n\nShuffle all cards in your trash into your deck and [[Ener Charge 2]]. Then, put up to two target #S SIGNI from your Ener Zone onto your field.\nThen, target #S SIGNI on your field gains [[S Lancer]] until end of turn.\n "
        );
        
        setName("en_fan", "LANCER IN THE DARK");
        setDescription("en_fan",
                "=U =E with 3 or more colors among them\n\n" +
                "Shuffle all cards from your trash into your deck, and [[Ener Charge 2]].\n" +
                "Then, put up to 2 #S @[Dissona]@ SIGNI from your ener zone onto the field.\n" +
                "Then, target 1 of your #S @[Dissona]@ SIGNI, and until end of turn, it gains [[S Lancer]]."
        );

		setName("zh_simplified", "LANCER IN THE DARK");
        setDescription("zh_simplified", 
                "=U=E持有合计3种类以上的颜色（你的场上的分身3只把这个条件满足）\n" +
                "你的废弃区的全部的牌加入牌组洗切，[[能量填充2]]。\n" +
                "然后，从你的能量区把#S的精灵2张最多作为对象，将这些出场。\n" +
                "然后，你的#S的精灵1只作为对象，直到回合结束时为止，其得到[[S枪兵]]。\n"
        );

        setCardFlags(CardFlag.DISSONA);

        setType(CardType.PIECE);
        setLRIGTeam(CardLRIGTeam.DREAM_TEAM);
        setColor(CardColor.GREEN);
        setCost(Cost.color(CardColor.GREEN, 1));
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
            return CardAbilities.getColorsCount(getLRIGs(getOwner())) >= 3 ? ConditionState.OK : ConditionState.BAD;
        }
        private void onPieceEff()
        {
            returnToDeck(getCardsInTrash(getOwner()), DeckPosition.TOP);
            shuffleDeck();
            enerCharge(2);

            DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.FIELD).own().SIGNI().dissona().fromEner().playable());
            putOnField(data);
            
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).own().SIGNI().dissona()).get();
            attachAbility(target, new StockAbilitySLancer(), ChronoDuration.turnEnd());
        }
    }
}
