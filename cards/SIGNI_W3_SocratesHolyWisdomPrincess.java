package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.cost.EnerCost;

public final class SIGNI_W3_SocratesHolyWisdomPrincess extends Card {

    public SIGNI_W3_SocratesHolyWisdomPrincess()
    {
        setImageSets("WXDi-P11-039");

        setOriginalName("聖英姫　ソクラテス");
        setAltNames("セイエイキソクラテス Seieiki Sokuratesu");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、あなたの手札を公開してもよい。その後、この方法で公開したシグニのレベルの合計が１０の場合、対戦相手のシグニ１体を対象とし、手札から白のシグニを１枚捨てる。そうした場合、それを手札に戻す。\n" +
                "@A $T1 %X %X：あなたのデッキの上からカードを３枚見る。その中からカードを１枚まで手札に加え、残りを好きな順番でデッキの一番下に置く。"
        );

        setName("en", "Socrates, Blessed Mind Queen");
        setDescription("en",
                "@U: At the beginning of your attack phase, you may reveal your hand. Then, if the total level of SIGNI revealed this way is exactly ten, discard a white SIGNI. If you do, return target SIGNI on your opponent's field to its owner's hand.\n" +
                "@A $T1 %X %X: Look at the top three cards of your deck. Add up to one card from among them to your hand and put the rest on the bottom of your deck in any order."
        );
        
        setName("en_fan", "Socrates, Holy Wisdom Princess");
        setDescription("en_fan",
                "@U: At the beginning of your attack phase, you may reveal your hand. If the total level of the SIGNI revealed this way is 10 or more, target 1 of your opponent's SIGNI, and you may discard 1 white SIGNI from your hand. If you do, return it to their hand.\n" +
                "@A $T1 %X %X: Look at the top 3 cards of your deck. Add up to 1 card from among them to your hand, and put the rest on the bottom of your deck in any order."
        );

		setName("zh_simplified", "圣英姬 苏格拉底");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，可以把你的手牌公开。然后，这个方法公开的精灵的等级的合计在10的场合，对战对手的精灵1只作为对象，从手牌把白色的精灵1张舍弃。这样做的场合，将其返回手牌。\n" +
                "@A $T1 %X %X:从你的牌组上面看3张牌。从中把牌1张最多加入手牌，剩下的任意顺序放置到牌组最下面。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.WISDOM);
        setLevel(3);
        setPower(10000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);

            ActionAbility act = registerActionAbility(new EnerCost(Cost.colorless(2)), this::onActionEff);
            act.setUseLimit(UseLimit.TURN, 1);
        }

        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if(playerChoiceActivate())
            {
                reveal(getHandCount(getOwner()), getOwner(), CardLocation.HAND);
                
                if(new TargetFilter().own().SIGNI().fromRevealed().getExportedData().stream().mapToInt(c -> ((CardIndex)c).getIndexedInstance().getLevel().getValue()).sum() >= 10)
                {
                    CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).OP().SIGNI()).get();
                    
                    addToHand(getCardsInRevealed(getOwner()));
                    
                    if(target != null && discard(0,1, new TargetFilter().SIGNI().withColor(CardColor.WHITE)).get() != null)
                    {
                        addToHand(target);
                    }
                }
            }
        }

        private void onActionEff()
        {
            look(3);
            
            CardIndex cardIndex = playerTargetCard(0,1, new TargetFilter(TargetHint.HAND).own().fromLooked()).get();
            addToHand(cardIndex);
            
            while(getLookedCount() > 0)
            {
                cardIndex = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).own().fromLooked()).get();
                returnToDeck(cardIndex, DeckPosition.BOTTOM);
            }
        }
    }
}
