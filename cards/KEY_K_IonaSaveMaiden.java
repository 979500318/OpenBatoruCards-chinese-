package open.batoru.data.cards;

import open.batoru.core.Game;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.AttackModifierFlag;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardConst.UseTiming;
import open.batoru.data.Cost;
import open.batoru.data.DataTable;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.ConstantAbilityShared;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.cost.ExceedCost;
import open.batoru.data.ability.modifiers.AbilityGainModifier;
import open.batoru.data.ability.modifiers.AttackModifier;

public final class KEY_K_IonaSaveMaiden extends Card {

    public KEY_K_IonaSaveMaiden()
    {
        setImageSets("WXK01-035");

        setOriginalName("セーブ/メイデン　イオナ");
        setAltNames("セーブメイデンイオナ Seebu Meiden Iona");
        setDescription("jp",
                "@C：あなたのセンタールリグは以下の能力を得る。" +
                "@>@A -A @[エクシード３]@：このターン、対戦相手のシグニは可能ならばアタックしなければならない。このターン終了時、このターンにアタックしたすべてのシグニをバニッシュする。@@" +
                "@E：あなたのトラッシュからシグニ１枚を対象とし、それを手札に加える。\n" +
                "@A $T1 %K：対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－3000する。"
        );

        setName("en", "Iona, Save/Maiden");
        setDescription("en",
                "@C: Your center LRIG gains:" +
                "@>@A -A @[Exceed 3]@: This turn, your opponent's SIGNI must attack if able. At the end of this turn, banish all SIGNI that attacked this turn.@@" +
                "@E: Target 1 SIGNI from your trash, and add it to your hand.\n" +
                "@A $T1 %K: Target 1 of your opponent's SIGNI, and until end of turn, it gets --3000 power."
        );

		setName("zh_simplified", "救月/少女 伊绪奈");
        setDescription("zh_simplified", 
                "@C :你的核心分身得到以下的能力。\n" +
                "@>@A :攻击阶段@[超越 3]@（从你的分身的下面把牌合计3张放置到分身废弃区）这个回合，如果对战对手的精灵能攻击，则必须攻击。这个回合结束时，这个回合攻击过的全部的精灵破坏。@@@@\n" +
                "@E :从你的废弃区把精灵1张作为对象，将其加入手牌。\n" +
                "@A $T1 %K:对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-3000。\n"
        );

        setType(CardType.KEY);
        setColor(CardColor.BLACK);
        setCost(Cost.coin(1));

        setPlayFormat(PlayFormat.KEY);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerConstantAbility(new TargetFilter().own().LRIG(), new AbilityGainModifier(this::onConstEffModGetSample));
            
            registerEnterAbility(this::onEnterEff);
            
            ActionAbility act = registerActionAbility(new EnerCost(Cost.color(CardColor.BLACK, 1)), this::onActionEff);
            act.setUseLimit(UseLimit.TURN, 1);
        }
        
        private Ability onConstEffModGetSample(CardIndex cardIndex)
        {
            ActionAbility attachedAct = cardIndex.getIndexedInstance().registerActionAbility(new ExceedCost(3), this::onAttachedActionEff);
            attachedAct.setActiveUseTiming(UseTiming.ATTACK);
            return attachedAct;
        }
        private void onAttachedActionEff()
        {
            ConstantAbilityShared attachedConst = new ConstantAbilityShared(new TargetFilter().OP().SIGNI(), new AttackModifier(AttackModifierFlag.FORCE_ATTACK));
            attachPlayerAbility(getOwner(), attachedConst, ChronoDuration.turnEnd());
            
            callDelayedEffect(ChronoDuration.turnEnd(), () -> {
                DataTable<CardIndex> dataAttacked = new DataTable<>();
                Game.getCurrentGame().getGameLog().exportTurnRecords().stream().
                 filter(e -> e.getId() == GameEventId.ATTACK && !isOwnCard(e.getCaller()) && CardType.isSIGNI(e.getCaller().getCardReference().getType())).
                 forEachOrdered(e -> dataAttacked.add(e.getCallerCardIndex()));
                
                banish(dataAttacked);
            });
        }
        
        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).own().SIGNI().fromTrash()).get();
            addToHand(target);
        }
        
        private void onActionEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            gainPower(target, -3000, ChronoDuration.turnEnd());
        }
    }
}
