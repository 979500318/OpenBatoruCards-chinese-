package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.data.DataTable;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityConst;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.CardAbilities;
import open.batoru.data.ability.PieceAbility;

public final class PIECE_X_IdolLifeThatBeginsWithEveryone extends Card {

    public PIECE_X_IdolLifeThatBeginsWithEveryone()
    {
        setImageSets("WXDi-P10-004");

        setOriginalName("み～んなではじめるアイドルライフ！");
        setAltNames("ミーンナデハジメルアイドルライフ Miinna de Hajimeru Aidoru Raifu");
        setDescription("jp",
                "=U =E 合計３種類以上の色を持つ\n\n" +
                "以下の３つから２つまで選ぶ。\n" +
                "$$1あなたのデッキの上からカードを５枚見る。その中から＜プリパラ＞のシグニを２枚まで公開し手札に加え、残りを好きな順番でデッキの一番下に置く。その後、手札から＜プリパラ＞のシグニを好きな枚数場に出す。\n" +
                "$$2対戦相手のシグニ１体を対象とし、あなたの場に＜プリパラ＞のシグニが３体ある場合、それをバニッシュする。\n" +
                "$$3あなたの場にある＜プリパラ＞のシグニ１体につき【エナチャージ１】をする。"
        );

        setName("en", "Let's Begin Our Idol Life!");
        setDescription("en",
                "=U =E You have the three LRIG on your field with three or more different colors among all members.\n\n" +
                "Choose up to two of the following.\n" +
                "$$1 Look at the top five cards of your deck. Reveal up to two <<Pripara>> SIGNI from among them and add them to your hand. Put the rest on the bottom of your deck in any order. Then, put any number of <<Pripara>> SIGNI from your hand onto your field.\n" +
                "$$2 Vanish target SIGNI on your opponent's field if there are three <<Pripara>> SIGNI on your field.\n" +
                "$$3 [[Ener Charge 1]] for each <<Pripara>> SIGNI on your field."
        );
        
        setName("en_fan", "Idol Life That Begins With Everyone!");
        setDescription("en_fan",
                "=U =E with 3 or more colors among them\n\n" +
                "@[@|Choose up to 2 of the following:|@]@\n" +
                "$$1 Look at the top 5 cards of your deck. Reveal up to 2 <<PriPara>> SIGNI from among them, and add them to your hand, and put the rest on the bottom of your deck in any order. Then, put any number of <<PriPara>> SIGNI from your hand onto the field.\n" +
                "$$2 Target 1 of your opponent's SIGNI, and if there are 3 <<PriPara>> SIGNI on your field, banish it.\n" +
                "$$3 For each <<PriPara>> SIGNI on your field, [[Ener Charge 1]]."
        );

		setName("zh_simplified", "大~家一起开始偶像演出！");
        setDescription("zh_simplified", 
                "=U=E持有合计3种类以上的颜色（你的场上的分身3只把这个条件满足）\n" +
                "从以下的3种选2种最多。\n" +
                "$$1 从你的牌组上面看5张牌。从中把<<プリパラ>>精灵2张最多公开加入手牌，剩下的任意顺序放置到牌组最下面。然后，从手牌把<<プリパラ>>精灵任意张数出场。\n" +
                "$$2 对战对手的精灵1只作为对象，你的场上的<<プリパラ>>精灵在3只的场合，将其破坏。\n" +
                "$$3 依据你的场上的<<プリパラ>>精灵的数量，每有1只就[[能量填充1]]。\n"
        );

        setType(CardType.PIECE);
        setLRIGTeam(CardLRIGTeam.DREAM_TEAM);
        setUseTiming(UseTiming.MAIN);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        private final PieceAbility piece;
        public IndexedInstance(int cardId)
        {
            super(cardId);

            piece = registerPieceAbility(this::onPieceEff);
            piece.setCondition(this::onPieceEffCond);
            piece.setModeChoice(0,2);
        }

        private ConditionState onPieceEffCond()
        {
            return CardAbilities.getColorsCount(getLRIGs(getOwner())) >= 3 ? ConditionState.OK : ConditionState.BAD;
        }
        private void onPieceEff()
        {
            int modes = piece.getChosenModes();
            
            if((modes & 1<<0) != 0)
            {
                look(5);

                DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.HAND).own().SIGNI().withClass(CardSIGNIClass.PRIPARA).fromLooked());
                addToHand(data);
                
                while(getLookedCount() > 0)
                {
                    CardIndex cardIndex = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).own().fromLooked()).get();
                    returnToDeck(cardIndex, DeckPosition.BOTTOM);
                }
                
                data = playerTargetCard(0, AbilityConst.MAX_UNLIMITED, new TargetFilter(TargetHint.FIELD).own().SIGNI().withClass(CardSIGNIClass.PRIPARA).fromHand().playable());
                putOnField(data);
            }
            
            if((modes & 1<<1) != 0)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI()).get();
                
                if(new TargetFilter().own().SIGNI().withClass(CardSIGNIClass.PRIPARA).getValidTargetsCount() == 3)
                {
                    banish(target);
                }
            }
            
            if((modes & 1<<2) != 0)
            {
                enerCharge(new TargetFilter().own().SIGNI().withClass(CardSIGNIClass.PRIPARA).getValidTargetsCount());
            }
        }
    }
}
