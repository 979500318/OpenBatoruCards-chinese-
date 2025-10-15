package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.DataTable;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.cost.ExceedCost;
import open.batoru.data.ability.modifiers.AbilityGainModifier;

public final class KEY_R_HanayoActingInPerfectHarmony extends Card {
    
    public KEY_R_HanayoActingInPerfectHarmony()
    {
        setImageSets("WDK03-006", "SPK02-02");
        
        setOriginalName("異体同心　華代");
        setAltNames("イタイドウシンハナヨ Itai Doushin Hanayo");
        setDescription("jp",
                "@C：あなたのセンタールリグは以下の能力を得る。" +
                "@>@A -A @[エクシード２]@：対戦相手のシグニ１体を対象とし、それをバニッシュする。@@" +
                "@E：対戦相手のシグニ１体を対象とし、それをバニッシュする。あなたのセンタールリグが＜緑子＞の場合、代わりに対戦相手のシグニを２体まで対象とし、それらをバニッシュする。"
        );
        
        setName("en", "Hanayo, Acting in Perfect Harmony");
        setDescription("en",
                "@C: Your center LRIG gains:" +
                "@>@A -A @[Exceed 2]@: Target 1 of your opponent's SIGNI, and banish it.@@" +
                "@E: Target 1 of your opponent's SIGNI, and banish it. If your center LRIG is <<Midoriko>>, instead target up to 2 of your opponent's SIGNI, and banish them."
        );
        
		setName("zh_simplified", "异体同心 华代");
        setDescription("zh_simplified", 
                "@C :你的核心分身得到以下的能力。\n" +
                "@>@A :攻击阶段@[超越 2]@对战对手的精灵1只作为对象，将其破坏。@@@@\n" +
                "@E :对战对手的精灵1只作为对象，将其破坏。你的核心分身是<<緑子>>的场合，作为替代，对战对手的精灵2只最多作为对象，将这些破坏。\n"
        );

        setType(CardType.KEY);
        setColor(CardColor.RED);
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
            
            registerConstantAbility(new TargetFilter().own().LRIG(), new AbilityGainModifier(this::onConstEffModGetSample));
            
            registerEnterAbility(this::onEnterEff);
        }
        
        private Ability onConstEffModGetSample(CardIndex cardIndex)
        {
            ActionAbility attachedAct = cardIndex.getIndexedInstance().registerActionAbility(new ExceedCost(2), this::onAttachedActionEff);
            attachedAct.setActiveUseTiming(UseTiming.ATTACK);
            return attachedAct;
        }
        private void onAttachedActionEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI()).get();
            getAbility().getSourceCardIndex().getIndexedInstance().banish(target);
        }
        
        private void onEnterEff()
        {
            TargetFilter filter = new TargetFilter(TargetHint.BANISH).OP().SIGNI();
            DataTable<CardIndex> data;
            if(!getLRIG(getOwner()).getIndexedInstance().getLRIGType().matches(CardLRIGType.MIDORIKO))
            {
                data = playerTargetCard(filter);
            } else {
                data = playerTargetCard(0,2, filter);
            }
            banish(data);
        }
    }
}
