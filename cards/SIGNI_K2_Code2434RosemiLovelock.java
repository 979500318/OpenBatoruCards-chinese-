package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityConst.ActionHint;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.cost.DiscardCost;

public final class SIGNI_K2_Code2434RosemiLovelock extends Card {

    public SIGNI_K2_Code2434RosemiLovelock()
    {
        setImageSets("WXDi-CP01-050");

        setOriginalName("コード２４３４　ロゼミ ラブロック");
        setAltNames("コードニジサンジロゼミラブロック Koodo Nijisanji Rozemi Raburokku");
        setDescription("jp",
                "@U $T1：あなたのシグニ１体がトラッシュから場に出たとき、カードを１枚引き、手札を１枚捨てる。\n\n" +
                "@A @[手札から＜バーチャル＞のシグニを２枚捨てる]@：このカードをトラッシュから場に出す。" +
                "~#：あなたのトラッシュから＜バーチャル＞のシグニ１枚を対象とし、それを手札に加えるか場に出す。"
        );

        setName("en", "Rosemi Lovelock, Code 2434");
        setDescription("en",
                "@U $T1: When a SIGNI enters your field from the trash, draw a card and discard a card.\n\n@A Discard two <<Virtual>> SIGNI: Put this card from your trash onto your field. " +
                "~#Add target <<Virtual>> SIGNI from your trash to your hand or put it onto your field."
        );
        
        setName("en_fan", "Code 2434 Rosemi Lovelock");
        setDescription("en_fan",
                "@U $T1: When 1 of your SIGNI enters the field from your trash, draw 1 card and discard 1 card from your hand.\n\n" +
                "@A @[Discard 2 <<Virtual>> SIGNI from your hand]@: Put this card from your trash onto the field." +
                "~#Target 1 <<Virtual>> SIGNI from your trash, and add it to your hand or put it onto the field."
        );

		setName("zh_simplified", "2434代号 Rosemi Lovelock");
        setDescription("zh_simplified", 
                "@U $T1 :当你的精灵1只从废弃区出场时，抽1张牌，手牌1张舍弃。\n" +
                "@A 从手牌把<<バーチャル>>精灵2张舍弃:这张牌从废弃区出场。（这个能力只有在这张牌在废弃区的场合才能使用。）" +
                "~#从你的废弃区把<<バーチャル>>精灵1张作为对象，将其加入手牌或出场。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.VIRTUAL);
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

            AutoAbility auto = registerAutoAbility(GameEventId.ENTER, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            auto.setUseLimit(UseLimit.TURN, 1);

            ActionAbility act = registerActionAbility(new DiscardCost(2, new TargetFilter().SIGNI().withClass(CardSIGNIClass.VIRTUAL)), this::onActionEff);
            act.setCondition(this::onActionCondition);
            act.setActiveLocation(CardLocation.TRASH);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private ConditionState onAutoEffCond(CardIndex caller)
        {
            return isOwnCard(caller) && CardType.isSIGNI(caller.getCardReference().getType()) &&
                   caller.getOldLocation() == CardLocation.TRASH ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            draw(1);
            discard(1);
        }

        private ConditionState onActionCondition()
        {
            return isPlayable() ? ConditionState.OK : ConditionState.WARN;
        }
        private void onActionEff()
        {
            if(getCardIndex().getLocation() == CardLocation.TRASH)
            {
                putOnField(getCardIndex());
            }
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
