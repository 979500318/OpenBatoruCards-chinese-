package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.Enter;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.events.EventAttack;

public final class SIGNI_W3_BlockToyThirdPlayPrincess extends Card {

    public SIGNI_W3_BlockToyThirdPlayPrincess()
    {
        setImageSets("WX25-P1-053");

        setOriginalName("参ノ遊姫　ブロックトイ");
        setAltNames("サンノユウキブロックトイ San no Yuuki Burokku Toi");
        setDescription("jp",
                "@U：このシグニがアタックしたとき、そのアタック終了時、あなたのデッキの一番上を公開する。そのカードがシグニの場合、このシグニを場から手札に戻してもよい。そうした場合、そのシグニをダウン状態で場に出す。そのシグニの@E能力は発動しない。\n" +
                "@E %W：あなたのデッキの上からカードを４枚見る。その中からシグニ１枚を場に出し、残りを好きな順番でデッキの一番上に戻す。" +
                "~#：対戦相手のパワー10000以下のシグニ１体を対象とし、それを手札に戻す。"
        );

        setName("en", "Block Toy, Third Play Princess");
        setDescription("en",
                "@U: Whenever this SIGNI attacks, at the end of that attack, reveal the top card of your deck. If it is a SIGNI, you may return this SIGNI from the field to your hand. If you do, put that SIGNI onto the field downed. That SIGNI's @E abilities don't activate.\n" +
                "@E %W: Look at the top 4 cards of your deck. Put 1 SIGNI from among them onto the field, and put the rest on the top of your deck in any order." +
                "~#Target 1 of your opponent's SIGNI with power 10000 or less, and return it to their hand."
        );

		setName("zh_simplified", "叁之游姬 拼装积木");
        setDescription("zh_simplified", 
                "@U 当这只精灵攻击时，那次攻击结束时，你的牌组最上面公开。那张牌是精灵的场合，可以把这只精灵从场上返回手牌。这样做的场合，那张精灵以横置状态出场。那只精灵的@E能力不能发动。\n" +
                "@E %W:从你的牌组上面看4张牌。从中把精灵1张出场，剩下的任意顺序返回牌组最上面。" +
                "~#对战对手的力量10000以下的精灵1只作为对象，将其返回手牌。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.PLAYGROUND_EQUIPMENT);
        setLevel(3);
        setPower(10000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerAutoAbility(GameEventId.ATTACK, this::onAutoEff);
            
            registerEnterAbility(new EnerCost(Cost.color(CardColor.WHITE, 1)), this::onEnterEff);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private void onAutoEff()
        {
            callDelayedEffect(((EventAttack)getEvent()).requestPostAttackTrigger(), () -> {
                CardIndex cardIndex = reveal();
                if(cardIndex == null) return;
                
                if(!getCardIndex().isSIGNIOnField() || !CardType.isSIGNI(cardIndex.getCardReference().getType()) ||
                   !playerChoiceActivate() || !addToHand(getCardIndex()) || !putOnField(cardIndex, Enter.DOWNED | Enter.DONT_ACTIVATE))
                {
                    returnToDeck(cardIndex, DeckPosition.TOP);
                }
            });
        }
        
        private void onEnterEff()
        {
            look(4);
            
            CardIndex cardIndex = playerTargetCard(0,1, new TargetFilter(TargetHint.FIELD).own().SIGNI().fromLooked().playable()).get();
            putOnField(cardIndex);
            
            while(getLookedCount() > 0)
            {
                cardIndex = playerTargetCard(new TargetFilter(TargetHint.TOP).own().fromLooked()).get();
                returnToDeck(cardIndex, DeckPosition.TOP);
            }
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).OP().SIGNI().withPower(0,10000)).get();
            addToHand(target);
        }
    }
}
