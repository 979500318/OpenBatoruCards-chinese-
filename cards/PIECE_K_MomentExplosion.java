package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardConst.UseTiming;
import open.batoru.data.Cost;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.AbilityConst.Enter;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.ConstantAbilityShared;
import open.batoru.data.ability.cost.ExceedCost;
import open.batoru.data.ability.modifiers.AbilityGainModifier;

public final class PIECE_K_MomentExplosion extends Card {
    
    public PIECE_K_MomentExplosion()
    {
        setImageSets("WXDi-P07-005");
        
        setOriginalName("瞬間explosion");
        setAltNames("シュンカンエクスプロージャン Shunkan Ekusupuroojan");
        setDescription("jp",
                "このゲームの間、あなたのセンタールリグは以下の能力を得る。" +
                "@>@A -M -A @[エクシード４]@：あなたのトラッシュからシグニ１枚を対象とし、それを能力を持たないシグニとして場に出す。ターン終了時、それを場からトラッシュに置く。"
        );
        
        setName("en", "Instantaneous Explosion");
        setDescription("en",
                "Your Center LRIG gains the following ability for the duration of the game. \n" +
                "@>@A -M -A @[Exceed 4]@: Put target SIGNI from your trash onto your field as SIGNI with no abilities. Put this SIGNI on your field into its owner's trash at the end of turn."
        );
        
        setName("en_fan", "Moment explosion");
        setDescription("en_fan",
                "This game, your center LRIG gains:" +
                "@>@A -M -A @[Exceed 4]@: Target 1 of your SIGNI from your trash, and put it onto the field as a SIGNI with no abilities. At the end of the turn, put it into the trash."
        );
        
		setName("zh_simplified", "瞬间explosion");
        setDescription("zh_simplified", 
                "这场游戏期间，你的核心分身得到以下的能力。（成长后的新的核心分身依然得到能力）\n" +
                "@>@A 主要阶段:攻击阶段@[超越 4]@（从你的分身的下面把牌合计4张放置到分身废弃区）从你的废弃区把精灵1张作为对象，将其作为不持有能力的精灵出场。回合结束时，将其从场上放置到废弃区。@@\n"
        );

        setType(CardType.PIECE);
        setColor(CardColor.BLACK);
        setCost(Cost.color(CardColor.BLACK, 1));
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
            
            registerPieceAbility(this::onPieceEff);
        }
        
        private void onPieceEff()
        {
            ConstantAbilityShared attachedConst = new ConstantAbilityShared(new TargetFilter().own().LRIG(), new AbilityGainModifier(this::onAttachedConstEffModGetSample));
            attachPlayerAbility(getOwner(), attachedConst, ChronoDuration.permanent());
        }
        private Ability onAttachedConstEffModGetSample(CardIndex cardIndex)
        {
            ActionAbility attachedAct = cardIndex.getIndexedInstance().registerActionAbility(new ExceedCost(4), this::onAttachedActionEff);
            attachedAct.setActiveUseTiming(UseTiming.MAIN | UseTiming.ATTACK);
            
            return attachedAct;
        }
        private void onAttachedActionEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.FIELD).own().SIGNI().fromTrash().playable()).get();
            
            if(target != null)
            {
                getAbility().getSourceCardIndex().getIndexedInstance().putOnField(target, Enter.NO_ABILITIES);
                
                getAbility().getSourceCardIndex().getIndexedInstance().callDelayedEffect(ChronoDuration.turnEnd(), () -> {
                    if(target.isSIGNIOnField())
                    {
                        getAbility().getSourceCardIndex().getIndexedInstance().trash(target);
                    }
                });
            }
        }
    }
}
