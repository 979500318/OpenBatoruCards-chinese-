package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.core.gameplay.rulechecks.RuleCheck.RuleCheckState;
import open.batoru.core.gameplay.rulechecks.player.PlayerRuleCheckRegistry.PlayerRuleCheckType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.DataTable;
import open.batoru.data.ability.ConstantAbility;
import open.batoru.data.ability.modifiers.PlayerRuleCheckModifier;
import open.batoru.game.FieldData;
import open.batoru.game.gfx.GFX;
import open.batoru.game.gfx.GFXZoneUnderIndicator;

public final class LRIGA_G2_MidorikoCapability extends Card {

    public LRIGA_G2_MidorikoCapability()
    {
        setImageSets("WX24-P1-038");

        setOriginalName("緑姫・手腕");
        setAltNames("ミドリコシュワン Midoriko Shuwan");
        setDescription("jp",
                "@E：【エナチャージ２】をする。その後、あなたのエナゾーンからカードを２枚まで対象とし、それらを手札に加える。\n" +
                "@E：次の対戦相手のターン終了時まで、このルリグは@>@C：対戦相手は中央のシグニゾーンにあるシグニでアタックできない。@@を得る。"
        );

        setName("en", "Midoriko, Capability");
        setDescription("en",
                "@E: [[Ener Charge 2]]. Then, target up to 2 cards from your ener zone, and add them to your hand.\n" +
                "@E: Until the end of your opponent's next turn, this LRIG gains:" +
                "@>@C: Your opponent can't attack with the SIGNI in their center SIGNI zone."
        );

		setName("zh_simplified", "绿姬·手腕");
        setDescription("zh_simplified", 
                "@E :[[能量填充2]]。然后，从你的能量区把牌2张最多作为对象，将这些加入手牌。\n" +
                "@E :直到下一个对战对手的回合结束时为止，这只分身得到\n" +
                "@>@C :对战对手的中央的精灵区的精灵不能攻击。@@\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.MIDORIKO);
        setColor(CardColor.GREEN);
        setCost(Cost.colorless(1));
        setLevel(2);
        setLimit(+1);
        setUseTiming(UseTiming.MAIN);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerEnterAbility(this::onEnterEff1);
            registerEnterAbility(this::onEnterEff2);
        }

        private void onEnterEff1()
        {
            enerCharge(2);
            
            DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.HAND).own().fromEner());
            addToHand(data);
        }
        
        private void onEnterEff2()
        {
            ConstantAbility attachedConst = new ConstantAbility(new PlayerRuleCheckModifier<>(PlayerRuleCheckType.CAN_ATTACK, TargetFilter.HINT_OWNER_OP, data ->
                data.getSourceCardIndex().getLocation() == CardLocation.SIGNI_CENTER ? RuleCheckState.BLOCK : RuleCheckState.IGNORE)
            );
            
            GFX.attachToAbility(attachedConst, new GFXZoneUnderIndicator(getOpponent(),CardLocation.SIGNI_CENTER, "chain"));
            
            attachAbility(getCardIndex(), attachedConst, ChronoDuration.nextTurnEnd(getOpponent()));
        }
    }
}
