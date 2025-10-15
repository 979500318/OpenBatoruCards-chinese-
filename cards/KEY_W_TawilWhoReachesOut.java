package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.CardDataSIGNIClass;
import open.batoru.data.Cost;
import open.batoru.data.DataTable;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.Enter;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.cost.ExceedCost;
import open.batoru.data.ability.modifiers.AbilityGainModifier;

import java.util.List;
import java.util.stream.Stream;

public final class KEY_W_TawilWhoReachesOut extends Card {

    public KEY_W_TawilWhoReachesOut()
    {
        setImageSets("WX22-006");

        setOriginalName("差し伸べし者　タウィル");
        setAltNames("サシノベシモノタウィル Sashinobe Shimono Tauiru");
        setDescription("jp",
                "@C：あなたのセンタールリグは以下の能力を得る。" +
                "@>@A $T1 -A @[エクシード２]@：あなたのデッキからレベル４以下のシグニ１枚を探して場に出し、デッキをシャッフルする。そのシグニの@E能力は発動しない。\n" +
                "@A $T1 -A @[エクシード２]@：あなたのトラッシュから＜精元＞ではないそれぞれ名前の異なる対象のシグニ７枚をデッキに加えてシャッフルする。この方法で共通するクラスを持つシグニ７枚をデッキに加えた場合、対象の対戦相手のシグニ１体をトラッシュに置く。"
        );

        setName("en", "Tawil, Who Reaches Out");
        setDescription("en",
                "@C: Your center LRIG gains:" +
                "@>@A $T1 -A @[Exceed 2]@: Search your deck for 1 level 4 or lower SIGNI, put it onto the field, and shuffle your deck. That SIGNI's @E abilities don't activate.\n" +
                "@A $T1 -A @[Exceed 2]@: Target 7 non-<<Origin Spirit>> SIGNI with different names from your trash, and shuffle them into your deck. If you shuffled 7 SIGNI that shared a common class this way, target 1 of your opponent's SIGNI, and put it into the trash."
        );

		setName("zh_simplified", "伸援者 塔维尔");
        setDescription("zh_simplified", 
                "@C :你的核心分身得到以下的能力。\n" +
                "@>@A $T1 :攻击阶段@[超越 2]@从你的牌组找等级4以下的精灵1张出场，牌组洗切。那只精灵的@E能力不能发动。@@\n" +
                "@A $T1 :攻击阶段@[超越 2]@\n" +
                "从你的废弃区把不是<<精元>>的名字不同的对象的精灵7张加入牌组洗切。这个方法把持有共通类别的精灵7张加入牌组的场合，对象的对战对手的精灵1只放置到废弃区。\n"
        );

        setType(CardType.KEY);
        setColor(CardColor.WHITE);
        setCost(Cost.coin(2));

        setPlayFormat(PlayFormat.KEY);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerConstantAbility(new TargetFilter().own().LRIG(), new AbilityGainModifier(this::onConstEffMod1GetSample),new AbilityGainModifier(this::onConstEffMod2GetSample));
        }

        private Ability onConstEffMod1GetSample(CardIndex cardIndex)
        {
            ActionAbility attachedAct1 = cardIndex.getIndexedInstance().registerActionAbility(new ExceedCost(2), this::onAttachedActionEff1);
            attachedAct1.setUseLimit(UseLimit.TURN, 1);
            attachedAct1.setActiveUseTiming(UseTiming.ATTACK);
            return attachedAct1;
        }
        private void onAttachedActionEff1()
        {
            CardIndex cardIndex = searchDeck(0,1, new TargetFilter(TargetHint.FIELD).own().SIGNI().withLevel(0,4).playable()).get();
            putOnField(cardIndex, Enter.DONT_ACTIVATE);
            
            shuffleDeck();
        }

        private Ability onConstEffMod2GetSample(CardIndex cardIndex)
        {
            ActionAbility attachedAct2 = cardIndex.getIndexedInstance().registerActionAbility(new ExceedCost(2), this::onAttachedActionEff2);
            attachedAct2.setCondition(this::onAttachedActionEff2Cond);
            attachedAct2.setUseLimit(UseLimit.TURN, 1);
            attachedAct2.setActiveUseTiming(UseTiming.ATTACK);
            attachedAct2.setNestedDescriptionOffset(1);
            return attachedAct2;
        }
        private ConditionState onAttachedActionEff2Cond()
        {
            return canConditionBeFulfilled(new TargetFilter(TargetHint.TRASH).own().SIGNI().not(new TargetFilter().withClass(CardSIGNIClass.ORIGIN, CardSIGNIClassGeneration.SPIRIT)).fromTrash().getExportedData().stream()) ? ConditionState.OK : ConditionState.WARN;
        }
        private void onAttachedActionEff2()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.TRASH).OP().SIGNI()).get();
            
            TargetFilter filter = new TargetFilter(TargetHint.SHUFFLE).own().SIGNI().not(new TargetFilter().withClass(CardSIGNIClass.ORIGIN, CardSIGNIClassGeneration.SPIRIT)).fromTrash();
            if(canConditionBeFulfilled(filter.getExportedData().stream()))
            {
                DataTable<CardIndex> data = playerTargetCard(7, filter, this::onAttachedActionEff2TargetCond);
                if(data.size() == 7)
                {
                    boolean match = CardDataSIGNIClass.shareCommonClass(data);
                    if(returnToDeck(data, DeckPosition.TOP) == 7 && shuffleDeck() && match)
                    {
                        trash(target);
                    }
                }
            }
        }
        private boolean onAttachedActionEff2TargetCond(List<CardIndex> listPickedCards)
        {
            return listPickedCards.size() == 7 && canConditionBeFulfilled(listPickedCards.stream());
        }
        private boolean canConditionBeFulfilled(Stream<? super CardIndex> stream)
        {
            return stream.map(c -> ((CardIndex)c).getCardReference().getOriginalName()).distinct().count() >= 7;
        }
    }
}
