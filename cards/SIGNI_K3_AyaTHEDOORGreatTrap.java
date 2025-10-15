package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.cost.CoinCost;
import open.batoru.data.ability.events.EventCoin;

public final class SIGNI_K3_AyaTHEDOORGreatTrap extends Card {

    public SIGNI_K3_AyaTHEDOORGreatTrap()
    {
        setImageSets("WXDi-P15-055", "WXDi-P15-055P");

        setOriginalName("大罠　あや//THE DOOR");
        setAltNames("ダイビンアヤザドアー Daibin Aya Za Doaa");
        setDescription("jp",
                "@U $T1：あなたのターンの間、あなたが#Cを１枚以上支払ったとき、あなたのトラッシュから＜闘争派＞のシグニを１枚まで対象とし、それを場に出す。\n" +
                "@A #C：あなたのデッキの上からカードを３枚トラッシュに置く。\n" +
                "@A $T1 #C #C：ターン終了時まで、このシグニは@>@U：このシグニがアタックしたとき、対戦相手のデッキの上からカードを６枚トラッシュに置く。@@を得る。" +
                "~#：対戦相手のレベル２以下のシグニ１体を対象とし、それをバニッシュする。"
        );

        setName("en", "Aya//THE DOOR, Master Trickster");
        setDescription("en",
                "@U $T1: During your turn, when you pay one or more #C, put up to one target <<War Division>> SIGNI from your trash onto your field.\n@A #C: Put the top three cards of your deck into your trash.\n@A $T1 #C #C: This SIGNI gains@>@U: Whenever this SIGNI attacks, put the top six cards of your opponent's deck into their trash.@@until end of turn." +
                "~#Vanish target level two or less SIGNI on your opponent's field."
        );
        
        setName("en_fan", "Aya//THE DOOR, Great Trap");
        setDescription("en_fan",
                "@U $T1: During your turn, when you pay 1 or more #C, target up to 1 <<Struggle Faction>> SIGNI from your trash, and put it onto the field.\n" +
                "@A #C: Put the top 3 cards of your deck into the trash.\n" +
                "@A $T1 #C #C: Until end of turn, this SIGNI gains:" +
                "@>@U: Whenever this SIGNI attacks, put the top 6 cards of your opponent's deck into the trash.@@" +
                "~#Target 1 of your opponent's level 2 or lower SIGNI, and banish it."
        );

		setName("zh_simplified", "大罠 亚弥//THE DOOR");
        setDescription("zh_simplified", 
                "@U $T1 你的回合期间，当你把币:1个以上支付时，从你的废弃区把<<闘争派>>精灵1张最多作为对象，将其出场。\n" +
                "@A #C:从你的牌组上面把3张牌放置到废弃区。\n" +
                "@A $T1 #C #C:直到回合结束时为止，这只精灵得到\n" +
                "@>@U :当这只精灵攻击时，从对战对手的牌组上面把6张牌放置到废弃区。@@" +
                "~#对战对手的等级2以下的精灵1只作为对象，将其破坏。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.STRUGGLE_FACTION,CardSIGNIClass.TRICK);
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

            AutoAbility auto = registerAutoAbility(GameEventId.COIN, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            auto.setUseLimit(UseLimit.TURN, 1);

            registerActionAbility(new CoinCost(1), this::onActionEff1);

            ActionAbility act2 = registerActionAbility(new CoinCost(2), this::onActionEff2);
            act2.setUseLimit(UseLimit.TURN, 1);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && isOwnCard(getEvent().getSource()) && EventCoin.getDataGainedCoins() < 0 ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            CardIndex target = playerTargetCard(0,1, new TargetFilter(TargetHint.FIELD).own().SIGNI().withClass(CardSIGNIClass.STRUGGLE_FACTION).fromTrash().playable()).get();
            putOnField(target);
        }
        
        private void onActionEff1()
        {
            millDeck(3);
        }
        
        private void onActionEff2()
        {
            AutoAbility attachedAuto = new AutoAbility(GameEventId.ATTACK, this::onAttachedAutoEff);
            attachAbility(getCardIndex(), attachedAuto, ChronoDuration.turnEnd());
        }
        private void onAttachedAutoEff()
        {
            millDeck(getOpponent(), 6);
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withLevel(0,2)).get();
            banish(target);
        }
    }
}
