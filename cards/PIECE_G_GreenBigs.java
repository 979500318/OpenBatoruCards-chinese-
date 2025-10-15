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
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.ConstantAbilityShared;
import open.batoru.data.ability.cost.ExceedCost;
import open.batoru.data.ability.modifiers.AbilityGainModifier;

public final class PIECE_G_GreenBigs extends Card {
    
    public PIECE_G_GreenBigs()
    {
        setImageSets("WXDi-P06-005");
        
        setOriginalName("グリーン・ビッグス");
        setAltNames("グリーンビッグス Guriin Biggusu");
        setDescription("jp",
                "このゲームの間、あなたのセンタールリグは以下の能力を得る。" +
                "@>@A -M -A @[エクシード４]@：あなたのシグニ１体を対象とし、ターン終了時まで、それのパワーを＋10000する。"
        );
        
        setName("en", "Green Bigs");
        setDescription("en",
                "Your Center LRIG gains the following ability for the duration of the game. \n" +
                "@>@A -M -A @[Exceed 4]@: Target SIGNI on your field gets +10000 power until end of turn."
        );
        
        setName("en_fan", "Green Bigs");
        setDescription("en_fan",
                "This game, your center LRIG gains:" +
                "@>@A -M -A @[Exceed 4]@: Target 1 of your SIGNI, and until end of turn, it gets +10000 power."
        );
        
		setName("zh_simplified", "翠绿·大化");
        setDescription("zh_simplified", 
                "这场游戏期间，你的核心分身得到以下的能力。（成长后的新的核心分身依然得到能力）\n" +
                "@>@A 主要阶段:攻击阶段@[超越 4]@（从你的分身的下面把牌合计4张放置到分身废弃区）你的精灵1只作为对象，直到回合结束时为止，其的力量+10000。@@\n"
        );

        setType(CardType.PIECE);
        setColor(CardColor.GREEN);
        setCost(Cost.color(CardColor.GREEN, 1));
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
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.PLUS).own().SIGNI()).get();
            getAbility().getSourceCardIndex().getIndexedInstance().gainPower(target, 10000, ChronoDuration.turnEnd());
        }
    }
}
