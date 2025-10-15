package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.Enter;
import open.batoru.data.ability.cost.EnerCost;

public final class LRIGA_W2_AssistAngeLevel2Alchemy extends Card {

    public LRIGA_W2_AssistAngeLevel2Alchemy()
    {
        setImageSets("WXDi-CP01-015");

        setOriginalName("【アシスト】アンジュ　レベル２【錬金】");
        setAltNames("アシストアンジュレベルニレンキン Ashisuto Anju Reberu Ni Renkin Assist Ange");
        setDescription("jp",
                "@E：対戦相手のレベル２以上のシグニ１体を対象とし、それをトラッシュに置く。対戦相手は自分のデッキの上からそれよりレベルの低いシグニがめくれるまで公開し、そのシグニを場に出す。そのシグニの@E能力は発動しない。この方法で公開されたカードをシャッフルしてデッキの一番下に置く。\n" +
                "@E %W %X：あなたのトラッシュから#Gを持つシグニ１枚を対象とし、それを手札に加える。"
        );

        setName("en", "[Assist] Ange, Level 2 [Alchemy]");
        setDescription("en",
                "@E: Put target level two or more SIGNI on your opponent's field into its owner's trash. Your opponent reveals the top card of their deck until they turn over a SIGNI with a level less than it. They put that SIGNI onto their field. The @E abilities of SIGNI put onto their field this way do not activate. They put cards revealed this way on the bottom of their deck in a random order.\n@E %W %X: Add target SIGNI with a #G from your trash to your hand."
        );
        
        setName("en_fan", "[Assist] Ange Level 2 [Alchemy]");
        setDescription("en_fan",
                "@E: Target 1 of your opponent's level 2 or higher SIGNI, and put it into the trash. Your opponent reveals the top card of their deck until a SIGNI with level lower than that is revealed, and puts that SIGNI onto the field. Its @E abilities don't activate. Shuffle the revealed this way cards and put them on the bottom of their deck.\n" +
                "@E %W %X: Target 1 #G @[Guard]@ SIGNI from your trash, and add it to your hand."
        );

		setName("zh_simplified", "【支援】安洁 等级2【炼金】");
        setDescription("zh_simplified", 
                "@E 对战对手的等级2以上的精灵1只作为对象，将其放置到废弃区。对战对手从自己的牌组上面直到把比其的等级低的精灵公开为止，那张精灵出场。那只精灵的@E能力不能发动。这个方法公开的牌洗切放置到牌组最下面。\n" +
                "@E %W%X从你的废弃区把持有#G的精灵1张作为对象，将其加入手牌。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.ANGE);
        setLRIGTeam(CardLRIGTeam.SANBAKA);
        setColor(CardColor.WHITE);
        setLevel(2);
        setLimit(+1);
        setUseTiming(UseTiming.MAIN | UseTiming.ATTACK);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerEnterAbility(this::onEnterEff1);
            registerEnterAbility(new EnerCost(Cost.color(CardColor.WHITE, 1) + Cost.colorless(1)), this::onEnterEff2);
        }

        private void onEnterEff1()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.TRASH).OP().SIGNI().withLevel(2,0)).get();
            
            if(target != null)
            {
                trash(target);
                
                CardIndex cardIndex = revealUntil(getOpponent(), cardIndexRevealed ->
                    CardType.isSIGNI(cardIndexRevealed.getIndexedInstance().getTypeByRef()) && 
                    cardIndexRevealed.getIndexedInstance().getLevelByRef() < 2
                );
                putOnField(cardIndex, Enter.DONT_ACTIVATE);
                
                int countReturned = returnToDeck(getCardsInRevealed(getOpponent()), DeckPosition.BOTTOM);
                shuffleDeck(getOpponent(), countReturned, DeckPosition.BOTTOM);
            }
        }

        private void onEnterEff2()
        {
            CardIndex cardIndex = playerTargetCard(new TargetFilter(TargetHint.HAND).own().SIGNI().withState(CardStateFlag.CAN_GUARD).fromTrash()).get();
            addToHand(cardIndex);
        }
    }
}
