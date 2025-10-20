package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameAction;
import open.batoru.core.gameplay.GameConst.*;
import open.batoru.core.gameplay.actions.ActionDown;
import open.batoru.core.gameplay.actions.ActionTrash;
import open.batoru.core.gameplay.actions.override.OverrideAction;
import open.batoru.core.gameplay.actions.override.OverrideAction.OverrideScope;
import open.batoru.core.gameplay.actions.override.OverrideActionList;
import open.batoru.core.gameplay.actions.override.OverrideActionList.OverrideFlag;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.core.gameplay.rulechecks.card.CardRuleCheckRegistry.CardRuleCheckType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.events.GameEvent;
import open.batoru.data.ability.modifiers.PowerModifier;
import open.batoru.data.ability.modifiers.RuleCheckModifier;

public final class SIGNI_R3_CuChulainnCrimsonGeneralPrincess extends Card {
    
    public SIGNI_R3_CuChulainnCrimsonGeneralPrincess()
    {
        setImageSets("WXDi-P06-034");
        
        setOriginalName("紅将姫　クーフーリン");
        setAltNames("コウショウキクーフーリン Koushouki Kuufuurin Cu Chulainn Cuchulainn");
        setDescription("jp",
                "=R あなたのトラッシュにある＜武勇＞のシグニ２枚を下に重ねて場に出す\n\n" +
                "@C：このシグニがバニッシュされる場合、代わりに「アップ状態のこのシグニをダウンし、このシグニの下からカード１枚とあなたのエナゾーンからカード１枚をトラッシュに置く。」をしてもよい。\n" +
                "@C：あなたの中央のシグニゾーンにあるシグニのパワーを＋3000する。" +
                "~#：対戦相手のパワー10000以下のシグニ１体を対象とし、それをバニッシュする。"
        );
        
        setName("en", "Cu Chulainn, Crimson General Queen");
        setDescription("en",
                "=R Put this SIGNI onto your field with two <<Brave>> SIGNI from your trash underneath it. \n" +
                "@C: If this SIGNI is vanished, instead you may \"down this upped SIGNI and put a card underneath this SIGNI and a card from your Ener Zone into your trash.\"\n" +
                "@C: SIGNI in your center SIGNI Zone get +3000 power." +
                "~#Vanish target SIGNI on your opponent's field with power 10000 or less."
        );
        
        setName("en_fan", "Cú Chulainn, Crimson General Princess");
        setDescription("en_fan",
                "=R Put this SIGNI onto the field with 2 <<Valor>> SIGNI from your trash under it\n\n" +
                "@C: If this SIGNI would be banished, you may \"down this upped SIGNI, and put 1 card from under this SIGNI and 1 card from your ener zone into the trash.\" instead.\n" +
                "@C: The SIGNI in your center SIGNI zone gets +3000 power." +
                "~#Target 1 of your opponent's SIGNI with power 10000 or less, and banish it."
        );
        
		setName("zh_simplified", "红将姬 库丘林");
        setDescription("zh_simplified", 
                "=R把你的废弃区的<<武勇>>精灵2张在下面重叠出场（在空的精灵区出场）\n" +
                "@C :这只精灵被破坏的场合，作为替代，可以把\n" +
                "@>:竖直状态的这只精灵横置，从这只精灵的下面把1张牌和从你的能量区把1张牌放置到废弃区。@@\n" +
                "@C :你的中央的精灵区的精灵的力量+3000。" +
                "~#对战对手的力量10000以下的精灵1只作为对象，将其破坏。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.VALOR);
        setLevel(3);
        setPower(12000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            setUseCondition(UseCondition.RISE, 2, new TargetFilter().withClass(CardSIGNIClass.VALOR).fromTrash());
            
            registerConstantAbility(new RuleCheckModifier<>(CardRuleCheckType.ACTION_OVERRIDE, data ->
                new OverrideAction(GameEventId.BANISH, OverrideScope.CALLER,OverrideFlag.NON_MANDATORY, this::onConstEffModOverrideCond,this::onConstEffModOverrideHandler))
            );
            
            registerConstantAbility(new TargetFilter().own().SIGNI().fromLocation(CardLocation.SIGNI_CENTER), new PowerModifier(3000));
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private boolean onConstEffModOverrideCond(CardIndex cardIndex, GameEvent event, Ability sourceAbilityRC)
        {
            return !isState(CardStateFlag.DOWNED) && getCardsUnderCount(CardUnderCategory.UNDER) > 0 && getEnerCount(getOwner()) > 0;
        }
        private void onConstEffModOverrideHandler(OverrideActionList list, Ability sourceAbilityRC)
        {
            list.addAction(new ActionDown(getCardIndex()));
            
            list.addTargetAction(new TargetFilter(TargetHint.TRASH).own().under(getCardIndex())).setOnActionCompleted(() -> {
                ((ActionTrash)list.getAction(3)).setCardIndex((CardIndex)list.getAction(1).getDataTable().get());
            });
            list.addTargetAction(new TargetFilter(TargetHint.TRASH).own().fromEner()).setOnActionCompleted(() -> {
                ((ActionTrash)list.getAction(4)).setCardIndex((CardIndex)list.getAction(2).getDataTable().get());
            });
            
            list.addAction(new ActionTrash());
            list.addAction(new ActionTrash());
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,10000)).get();
            banish(target);
        }
    }
}
