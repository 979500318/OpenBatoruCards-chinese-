package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardUnderCategory;
import open.batoru.core.gameplay.GameConst.CardUnderType;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardFlag;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.Ability.AbilityFlag;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.ConstantAbility;
import open.batoru.data.ability.SpellAbility;
import open.batoru.data.ability.modifiers.AbilityGainModifier;
import open.batoru.data.ability.modifiers.PowerModifier;

public final class SPELL_R_RoaringRacingOfCenturysEnd extends Card {
    
    public SPELL_R_RoaringRacingOfCenturysEnd()
    {
        setImageSets("WXDi-P05-060");
        setLinkedImageSets("WXDi-P05-034");
        
        setOriginalName("世紀末の爆走");
        setAltNames("セイキマツのバクソウ Seikimatsu no Bakusou");
        setDescription("jp",
                "あなたの赤のシグニ１体を対象とし、それの下にカードが無い場合、このカードをそれの下に置く。\n\n" +
                "@C：このカードの上にある赤のシグニは@>@U：あなたのアタックフェイズ開始時、[[エナチャージ１]]をする。@@を得る。\n" +
                "@C：このカードの上にある《コードアクセル　ヒャッハー》のパワーを＋2000する。" +
                "~#：対戦相手のアップ状態のシグニ１体を対象とし、それをバニッシュする。"
        );
        
        setName("en", "Apocalypse Drift");
        setDescription("en",
                "Put this card under target red SIGNI on your field if there are no cards underneath it.\n\n" +
                "@C: The red SIGNI on top of this card gains@>@U: At the beginning of your attack phase, [[Ener Charge 1]].@@" +
                "@C: \"Hyahha, Code: Accel\" on top of this card gets +2000 power." +
                "~#Vanish target upped SIGNI on your opponent's field."
        );
        
        setName("en_fan", "Roaring Racing of Century's End");
        setDescription("en_fan",
                "Target 1 of your red SIGNI, and if there are no cards under it, put this card under it.\n\n" +
                "@C: The red SIGNI on top of this card gains:" +
                "@>@U: At the beginning of your attack phase, [[Ener Charge 1]].@@" +
                "@C: The \"Code Accel Hyahha\" on top of this card gets +2000 power." +
                "~#Target 1 of your opponent's upped SIGNI, and banish it."
        );
        
		setName("zh_simplified", "世纪末的爆走");
        setDescription("zh_simplified", 
                "你的红色的精灵1只作为对象，其的下面没有牌的场合，这张牌放置到其的下面。\n" +
                "@C :这张牌的上面的红色的精灵得到\n" +
                "@>@U :你的攻击阶段开始时，[[能量填充1]]。@@\n" +
                "@C :这张牌的上面的《コードアクセル　ヒャッハー》的力量+2000。" +
                "~#对战对手的竖直状态的精灵1只作为对象，将其破坏。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SPELL);
        setColor(CardColor.RED);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        private final SpellAbility spell;
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            spell = registerSpellAbility(this::onSpellEffPreTarget, this::onSpellEff);
            
            ConstantAbility cont1 = registerConstantAbility(new TargetFilter().SIGNI().withColor(CardColor.RED).over(cardId), new AbilityGainModifier(this::onConstEffSharedModGetSample));
            cont1.setActiveUnderFlags(CardUnderCategory.UNDER);
            cont1.getFlags().addValue(AbilityFlag.IGNORE_LOCATION);
            
            ConstantAbility cont2 = registerConstantAbility(new TargetFilter().SIGNI().withName("コードアクセル　ヒャッハー").over(cardId), new PowerModifier(2000));
            cont2.setActiveUnderFlags(CardUnderCategory.UNDER);
            cont2.getFlags().addValue(AbilityFlag.IGNORE_LOCATION);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private void onSpellEffPreTarget()
        {
            spell.setTargets(playerTargetCard(new TargetFilter(TargetHint.UNDER).own().SIGNI().withColor(CardColor.RED)));
        }
        private void onSpellEff()
        {
            if(spell.getTarget() != null && spell.getTarget().getIndexedInstance().getCardsUnderCount(CardUnderCategory.UNDER) == 0)
            {
                attach(spell.getTarget(), getCardIndex(), CardUnderType.UNDER_GENERIC);
            }
        }
        
        private Ability onConstEffSharedModGetSample(CardIndex cardIndex)
        {
            AutoAbility attachedAuto = cardIndex.getIndexedInstance().registerAutoAbility(GameEventId.PHASE_START, this::onAttachedAutoEff);
            attachedAuto.setCondition(this::onAttachedAutoEffCond);
            
            return attachedAuto;
        }
        private ConditionState onAttachedAutoEffCond()
        {
            return getAbility().getSourceCardIndex().getIndexedInstance().isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAttachedAutoEff(CardIndex caller)
        {
            getAbility().getSourceCardIndex().getIndexedInstance().enerCharge(1);
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().upped()).get();
            banish(target);
        }
    }
}
