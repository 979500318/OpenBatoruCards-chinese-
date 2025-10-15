package open.batoru.data.cards;

import open.batoru.core.Game;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.events.EventCoin;

public final class SIGNI_KG3_UrithTHEDOORWickedVerdantDevilPrincess extends Card {

    public SIGNI_KG3_UrithTHEDOORWickedVerdantDevilPrincess()
    {
        setImageSets("WXDi-P16-057", "WXDi-P16-057P");

        setOriginalName("凶翠魔姫　ウリス//THE DOOR");
        setAltNames("キョウスイマキウリスザドアー Kyousuimaki Urisu Za Doaa");
        setDescription("jp",
                "@U $T1：あなたが#Cを１枚以上支払ったとき、以下の２つから１つを選ぶ。\n" +
                "$$1あなたのトラッシュから＜闘争派＞のシグニ１枚を対象とし、それをエナゾーンに置く。\n" +
                "$$2あなたのエナゾーンからシグニ１枚を対象とし、それを手札に加える。\n" +
                "@U：このシグニがアタックしたとき、このターンにあなたが#Cを合計３枚以上支払っていた場合、対戦相手のシグニ１体を対象とし、%K %G %Xを支払ってもよい。そうした場合、それをバニッシュする。"
        );

        setName("en", "Urith//THE DOOR, Doomed Jade Queen");
        setDescription("en",
                "@U $T1: When you pay one or more #C, choose one of the following.\n$$1Put target <<War Division>> SIGNI from your trash into your Ener Zone. \n$$2Add target SIGNI from your Ener Zone to your hand.\n@U: Whenever this SIGNI attacks, if you have paid a total of three or more #C this turn, you may pay %K %G %X. If you do, vanish target SIGNI on your opponent's field."
        );
        
        setName("en_fan", "Urith//THE DOOR, Wicked Verdant Devil Princess");
        setDescription("en_fan",
                "@U $T1: When you pay 1 or more #C, @[@|choose 1 of the following:|@]@\n" +
                "$$1 Target 1 <<Struggle Faction>> SIGNI from your trash, and put it into the ener zone.\n" +
                "$$2 Target 1 SIGNI from your ener zone, and add it to your hand.\n" +
                "@U: Whenever this SIGNI attacks, if you paid a total of 3 or more #C this turn, target 1 of your opponent's SIGNI, and you may pay %K %G %X. If you do, banish it."
        );

		setName("zh_simplified", "凶翠魔姬 乌莉丝//THE DOOR");
        setDescription("zh_simplified", 
                "@U $T1 当你把币:1个以上支付时，从以下的2种选1种。\n" +
                "$$1 从你的废弃区把<<闘争派>>精灵1张作为对象，将其放置到能量区。\n" +
                "$$2 从你的能量区把精灵1张作为对象，将其加入手牌。\n" +
                "@U 当这只精灵攻击时，这个回合你把币:合计3个以上支付过的场合，对战对手的精灵1只作为对象，可以支付%K%G%X。这样做的场合，将其破坏。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK, CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.STRUGGLE_FACTION,CardSIGNIClass.DEVIL);
        setLevel(3);
        setPower(10000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            AutoAbility auto1 = registerAutoAbility(GameEventId.COIN, this::onAutoEff1);
            auto1.setCondition(this::onAutoEff1Cond);
            auto1.setUseLimit(UseLimit.TURN, 1);
            
            registerAutoAbility(GameEventId.ATTACK, this::onAutoEff2);
        }
        
        private ConditionState onAutoEff1Cond()
        {
            return isOwnCard(getEvent().getSource()) && EventCoin.getDataGainedCoins() < 0 ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff1(CardIndex caller)
        {
            if(playerChoiceMode() == 1)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ENER).own().SIGNI().withClass(CardSIGNIClass.STRUGGLE_FACTION).fromTrash()).get();
                putInEner(target);
            } else {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).own().SIGNI().fromEner()).get();
                addToHand(target);
            }
        }
        
        private void onAutoEff2()
        {
            if(Game.getCurrentGame().getGameLog().exportTurnRecords().stream().
                filter(event -> event.getId() == GameEventId.COIN && isOwnCard(event.getSource()) && ((EventCoin)event).getGainedCoins() < 0).
                mapToInt(event -> ((EventCoin)event).getGainedCoins()).sum() <= -3)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI()).get();
                
                if(target != null && payEner(Cost.color(CardColor.BLACK, 1) + Cost.color(CardColor.GREEN, 1) + Cost.colorless(1)))
                {
                    banish(target);
                }
            }
        }
    }
}
