package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardDataImageSet.Mask;
import open.batoru.data.ability.AbilityEffectNoSource;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.SpellAbility;
import open.batoru.data.ability.cost.ExceedCost;

public final class SPELL_X_RapidAccumulation extends Card {
    
    public SPELL_X_RapidAccumulation()
    {
        setImageSets(Mask.IGNORE+"PR-Di013");
        
        setOriginalName("飛躍する蓄積");
        setAltNames("ヒヤクスルチクセキ Hiyaku suru Chikuseki");
        setDescription("jp",
                "このスペルを使用する際、使用コストとして追加でエクシード４を支払ってもよい。\n\n" +
                "以下の３つから１つを選ぶ。ターン終了時まで、あなたのセンタールリグは選んだ能力を得る。追加でエクシード４を支払っていた場合、２つを選ぶ。\n" +
                "$$1@>@U $T1：このルリグがアタックしたとき、カードを１枚引く。@@" +
                "$$2@>@U $T1：このルリグがアタックしたとき、[[エナチャージ１]]をする。@@" +
                "$$3@>@U $T1：このルリグがアタックしたとき、対戦相手のシグニ１体を対象とし、それをトラッシュに置く。"
        );
        
        setName("en", "Rapid Accumulation");
        setDescription("en",
                "While using this spell, you may pay an additional @[Exceed 4]@ for its use cost.\n\n" +
                "@[@|Choose 1 of the following:|@]@\n" +
                "((If you additionally paid @[Exceed 4]@, choose 2 instead.))\n" +
                "Until end of turn, your center LRIG gains the chosen ability:\n" +
                "$$1@>@U $T1: When this LRIG attacks, draw 1 card.@@" +
                "$$2@>@U $T1: When this LRIG attacks, [[Ener Charge 1]].@@" +
                "$$3@>@U $T1: When this LRIG attacks, target 1 of your opponent's SIGNI, and put it into the trash."
        );
        
		setName("zh_simplified", "飞跃的蓄积");
        setDescription("zh_simplified", 
                "这张魔法使用时，可以作为使用费用追加把@[超越 4]@支付。\n" +
                "从以下的3种选1种。直到回合结束时为止，你的核心分身得到选的能力。追加把@[超越 4]@支付过的场合，作为替代，选2种。\n" +
                "@>@U $T1 :当这只分身攻击时，抽1张牌。@@\n" +
                "@>@U $T1 :当这只分身攻击时，[[能量填充1]]。@@\n" +
                "@>@U $T1 :当这只分身攻击时，对战对手的精灵1只作为对象，将其放置到废弃区。@@\n"
        );

        setType(CardType.SPELL);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        private final SpellAbility spell;
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            spell = registerSpellAbility(this::onSpellEff);
            spell.setAdditionalCost(new ExceedCost(4));
            spell.setModeChoice(1);
            spell.setOnModesChosenPre(this::onSpellEffPreModeChoice);
        }
        
        private void onSpellEffPreModeChoice()
        {
            if(spell.hasPaidAdditionalCost())
            {
                spell.setModeChoice(2);
            }
        }
        private void onSpellEff()
        {
            int modes = spell.getChosenModes();
            
            if((modes & 1<<0) != 0)
            {
                attachAutoEff(this::onAttachedAutoEff1, 0);
            }
            if((modes & 1<<1) != 0)
            {
                attachAutoEff(this::onAttachedAutoEff2, 1);
            }
            if((modes & 1<<2) != 0)
            {
                attachAutoEff(this::onAttachedAutoEff3, 2);
            }
        }
        private void attachAutoEff(AbilityEffectNoSource effect, int nestedOffset)
        {
            AutoAbility attachedAuto = new AutoAbility(GameEventId.ATTACK, effect);
            attachedAuto.setNestedDescriptionOffset(nestedOffset);
            attachAbility(getLRIG(getOwner()), attachedAuto, ChronoDuration.turnEnd());
        }
        private void onAttachedAutoEff1()
        {
            getAbility().getSourceCardIndex().getIndexedInstance().draw(1);
        }
        private void onAttachedAutoEff2()
        {
            getAbility().getSourceCardIndex().getIndexedInstance().enerCharge(1);
        }
        private void onAttachedAutoEff3()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.TRASH).OP().SIGNI()).get();
            getAbility().getSourceCardIndex().getIndexedInstance().trash(target);
        }
    }
}
