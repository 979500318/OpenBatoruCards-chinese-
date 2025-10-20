package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityConst.ActionHint;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.cost.DownCost;
import open.batoru.data.ability.events.EventMove;

public final class SIGNI_K3_Code2434RinShizuka extends Card {
    
    public SIGNI_K3_Code2434RinShizuka()
    {
        setImageSets("WXDi-D02-25");
        
        setOriginalName("コード２４３４　静凛");
        setAltNames("コードニジサンジシズカリン Koodo Nijisanji Shizuka Rin");
        setDescription("jp",
                "@U $T1：あなたのメインフェイズの間、あなたの他のシグニが場から離れたとき、このシグニをアップする。\n" +
                "@A #D：あなたのデッキの上からカードを５枚トラッシュに置く。" +
                "~#：あなたのトラッシュから＜バーチャル＞のシグニを１枚を対象とし、それを手札に加えるか場に出す。"
        );
        
        setName("en", "Rin Shizuka, Code 2434");
        setDescription("en",
                "@U $T1: During your main phase, when another SIGNI on your field leaves the field, up this SIGNI.\n" +
                "@A #D: Put the top five cards of your deck into the trash." +
                "~#Add target <<Virtual>> SIGNI from your trash to your hand or put it onto your field."
        );
        
        setName("en_fan", "Code 2434 Rin Shizuka");
        setDescription("en_fan",
                "@U $T1: During your main phase, when 1 of your other SIGNI leaves the field, up this SIGNI.\n" +
                "@A #D: Put the top 5 cards of your deck into the trash." +
                "~#Target 1 <<Virtual>> SIGNI from your trash, and add it to your hand or put it onto the field."
        );
        
		setName("zh_simplified", "2434代号 静凛");
        setDescription("zh_simplified", 
                "@U $T1 :你的主要阶段期间，当你的其他的精灵离场时，这只精灵竖直。\n" +
                "@A 横置:从你的牌组上面把5张牌放置到废弃区。（如果牌组5张以下，那么这些全部放置到废弃区并重构）" +
                "~#从你的废弃区把<<バーチャル>>精灵1张作为对象，将其加入手牌或出场。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.VIRTUAL);
        setLevel(3);
        setPower(12000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            AutoAbility auto = registerAutoAbility(GameEventId.MOVE, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            auto.setUseLimit(UseLimit.TURN, 1);
            
            registerActionAbility(new DownCost(), this::onActionEff);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private ConditionState onAutoEffCond(CardIndex cardIndex)
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.MAIN && isOwnCard(cardIndex) && cardIndex != getCardIndex() &&
                   cardIndex.isSIGNIOnField() && !CardLocation.isSIGNI(EventMove.getDataMoveLocation()) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex cardIndex)
        {
            up();
        }
        
        private void onActionEff()
        {
            millDeck(5);
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter().own().SIGNI().withClass(CardSIGNIClass.VIRTUAL).fromTrash()).get();
            
            if(target != null)
            {
                if(playerChoiceAction(ActionHint.HAND, ActionHint.FIELD) == 1)
                {
                    addToHand(target);
                } else {
                    putOnField(target);
                }
            }
        }
    }
}
