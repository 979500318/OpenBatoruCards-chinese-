package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.ActionHint;
import open.batoru.data.ability.cost.DownCost;
import open.batoru.data.ability.stock.StockAbilityAssassin;
import open.batoru.data.ability.stock.StockAbilityShadow;

public final class SIGNI_W3_YRecorderGunGreatTrap extends Card {

    public SIGNI_W3_YRecorderGunGreatTrap()
    {
        setImageSets("SPDi43-06");
        setLinkedImageSets("SPDi43-01");

        setOriginalName("大罠　Yリコーダーガン");
        setAltNames("ダイビンワイリコーダーガン Daibin Wai Rikoodaa Gan");
        setDescription("jp",
                "@U：このシグニがアタックしたとき、対戦相手は%X %Xを支払ってもよい。そうした場合、このアタックを無効にする。\n" +
                "@A @[アップ状態の他のシグニ１体をダウンする]@：あなたの場に《ゆかゆか☆さんさんきらきら》がいる場合、次の対戦相手のターン終了時まで、このシグニは[[アサシン（レベル２以下のシグニ）]]か[[シャドウ（レベル３以上のシグニ）]]を得る。"
        );

        setName("en", "Y Recorder Gun, Great Trap");
        setDescription("en",
                "@U: Whenever this SIGNI attacks, your opponent may pay %X %X. If they do, disable that attack.\n" +
                "@A @[Down 1 other upped SIGNI]@: If your LRIG is \"Yukayuka☆Three Three Kira Kira\", until the end of your opponent's next turn, this SIGNI gains [[Assassin (level 2 or lower SIGNI)]] or [[Shadow (level 3 or higher SIGNI)]]."
        );

		setName("zh_simplified", "大罠 Y转轮手枪");
        setDescription("zh_simplified", 
                "@U :当这只精灵攻击时，对战对手可以支付%X %X。这样做的场合，那次攻击无效。\n" +
                "@A 竖直状态的其他的精灵1只横置:你的场上有《ゆかゆか☆さんさんきらきら》的场合，直到下一个对战对手的回合结束时为止，这只精灵得到[[暗杀（等级2以下的精灵）]]或[[暗影（等级3以上的精灵）]]。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.TRICK);
        setLevel(3);
        setPower(12000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerAutoAbility(GameEventId.ATTACK, this::onAutoEff);

            registerActionAbility(new DownCost(new TargetFilter().SIGNI().except(cardId)), this::onActionEff);
        }
        
        private void onAutoEff()
        {
            if(payEner(getOpponent(), Cost.colorless(2)))
            {
               disableNextAttack(getCardIndex()); 
            }
        }

        private void onActionEff()
        {
            if(getLRIG(getOwner()).getIndexedInstance().getName().getValue().contains("ゆかゆか☆さんさんきらきら"))
            {
                if(playerChoiceAction(ActionHint.ASSASSIN, ActionHint.SHADOW) == 1)
                {
                    attachAbility(getCardIndex(), new StockAbilityAssassin(this::onAttachedStockEff1AddCond), ChronoDuration.nextTurnEnd(getOpponent()));
                } else {
                    attachAbility(getCardIndex(), new StockAbilityShadow(this::onAttachedStockEff2AddCond), ChronoDuration.nextTurnEnd(getOpponent()));
                }
            }
        }
        private ConditionState onAttachedStockEff1AddCond(CardIndex cardIndexOpposite)
        {
            return cardIndexOpposite.getIndexedInstance().getLevel().getValue() <= 2 ? ConditionState.OK : ConditionState.BAD;
        }
        private ConditionState onAttachedStockEff2AddCond(CardIndex cardIndexSource)
        {
            return CardType.isSIGNI(cardIndexSource.getCardReference().getType()) &&
                   cardIndexSource.getIndexedInstance().getLevel().getValue() >= 3 ? ConditionState.OK : ConditionState.BAD;
        }
    }
}
