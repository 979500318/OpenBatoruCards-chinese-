package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameLog;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.cost.CoinCost;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.events.EventCoin;

public final class SIGNI_K2_AlfouTHEDOORWickedDevil extends Card {

    public SIGNI_K2_AlfouTHEDOORWickedDevil()
    {
        setImageSets("WXDi-P16-083");

        setOriginalName("凶魔　アルフォウ//THE DOOR");
        setAltNames("キョウマアルフォウザドアー Kyouma Arufou Za Doaa");
        setDescription("jp",
                "@A $T1 %K：あなたのトラッシュから＜闘争派＞のシグニ１枚を対象とし、それを場に出す。このターン終了時、このターンにあなたが#Cを合計１枚以上支払っていなかった場合、それをトラッシュに置く。\n" +
                "@A $T1 #C：あなたのデッキの上からカードを３枚トラッシュに置く。" +
                "~#：あなたのトラッシュからシグニ１枚を対象とし、それを手札に加える。"
        );

        setName("en", "Alfou//THE DOOR, Doomed Evil");
        setDescription("en",
                "@A $T1 %K: Put target <<War Division>> SIGNI from your trash onto your field. At the end of this turn, if you haven't paid a total of one or more #C this turn, put it into its owner's trash.\n@A $T1 #C: Put the top three cards of your deck into your trash." +
                "~#Add target SIGNI from your trash to your hand."
        );
        
        setName("en_fan", "Alfou//THE DOOR, Wicked Devil");
        setDescription("en_fan",
                "@A $T1 %K: Target 1 <<Struggle Faction>> SIGNI from your trash, and put it onto the field. At the end of this turn, if you didn't pay 1 or more #C this turn, put it into the trash.\n" +
                "@A $T1 #C: Put the top 3 cards of your deck into the trash." +
                "~#Target 1 SIGNI from your trash, and add it to your hand."
        );

		setName("zh_simplified", "凶魔 阿尔芙//THE DOOR");
        setDescription("zh_simplified", 
                "@A $T1 %K从你的废弃区把<<闘争派>>精灵1张作为对象，将其出场。这个回合结束时，这个回合你没有把币:合计1个以上支付过的场合，将其放置到废弃区。\n" +
                "@A $T1 #C:从你的牌组上面把3张牌放置到废弃区。" +
                "~#从你的废弃区把精灵1张作为对象，将其加入手牌。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.STRUGGLE_FACTION,CardSIGNIClass.DEVIL);
        setLevel(2);
        setPower(5000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            ActionAbility act1 = registerActionAbility(new EnerCost(Cost.color(CardColor.BLACK, 1)), this::onActionEff1);
            act1.setUseLimit(UseLimit.TURN, 1);

            ActionAbility act2 = registerActionAbility(new CoinCost(1), this::onActionEff2);
            act2.setUseLimit(UseLimit.TURN, 1);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private void onActionEff1()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.FIELD).own().SIGNI().withClass(CardSIGNIClass.STRUGGLE_FACTION).fromTrash().playable()).get();
            
            if(putOnField(target))
            {
                int instanceId = target.getIndexedInstance().getInstanceId();
                callDelayedEffect(ChronoDuration.turnEnd(), () -> {
                    if(target.isSIGNIOnField() && target.getIndexedInstance().getInstanceId() == instanceId &&
                       GameLog.getTurnRecordsCount(event -> event.getId() == GameEventId.COIN && isOwnCard(event.getSource()) && ((EventCoin)event).getGainedCoins() < 0) == 0)
                    {
                        trash(target);
                    }
                });
            }
        }
        
        private void onActionEff2()
        {
            millDeck(3);
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).own().SIGNI().fromTrash()).get();
            addToHand(target);
        }
    }
}
