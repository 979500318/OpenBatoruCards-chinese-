package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.cost.MillCost;

public final class SIGNI_K2_AgaresWickedDevil extends Card {
    
    public SIGNI_K2_AgaresWickedDevil()
    {
        setImageSets("WXDi-P02-086");
        
        setOriginalName("凶魔　アガレス");
        setAltNames("キョウマアガレス Kyouma Agaresu");
        setDescription("jp",
                "@A $T1 @[デッキの上からカードを２枚トラッシュに置く]@：ターン終了時まで、このシグニのパワーを＋2000する。" +
                "~#：あなたのトラッシュから黒のシグニ１枚を対象とし、それを手札に加える。対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－3000する。"
        );
        
        setName("en", "Agares, Doomed Evil");
        setDescription("en",
                "@A $T1 @[Put the top two cards of your deck into your trash]@: This SIGNI gets +2000 power until end of turn." +
                "~#Add target black SIGNI from your trash to your hand. Target SIGNI on your opponent's field gets --3000 power until end of turn."
        );
        
        setName("en_fan", "Agares, Wicked Devil");
        setDescription("en_fan",
                "@A $T1 @[Put the top 2 cards of your deck into the trash]@: Until end of turn, this SIGNI gets +2000 power." +
                "~#Target 1 black SIGNI from your trash, and add it to your hand. Target 1 of your opponent's SIGNI, and until end of turn, it gets --3000 power."
        );
        
		setName("zh_simplified", "凶魔 阿加雷斯 ");
        setDescription("zh_simplified", 
                "@A $T1 从牌组上面把2张牌放置到废弃区:直到回合结束时为止，这只精灵的力量+2000。" +
                "~#从你的废弃区把黑色的精灵1张作为对象，将其加入手牌。对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-3000。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.DEVIL);
        setLevel(2);
        setPower(8000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            ActionAbility act = registerActionAbility(new MillCost(2), this::onActionEff);
            act.setUseLimit(UseLimit.TURN, 1);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private void onActionEff()
        {
            gainPower(getCardIndex(), 2000, ChronoDuration.turnEnd());
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).own().SIGNI().withColor(CardColor.BLACK).fromTrash()).get();
            addToHand(target);
            
            target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            gainPower(target, -3000, ChronoDuration.turnEnd());
        }
    }
}
