package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.AbilityCostList;
import open.batoru.data.ability.ConstantAbilityShared;
import open.batoru.data.ability.cost.DownCost;
import open.batoru.data.ability.cost.TrashCost;
import open.batoru.data.ability.modifiers.AbilityGainModifier;
import open.batoru.data.ability.stock.StockAbilityGuard;

public final class SIGNI_W2_ReinaHidakaDenonbu extends Card {

    public SIGNI_W2_ReinaHidakaDenonbu()
    {
        setImageSets("WXDi-P14-076", "WXDi-P14-076P");

        setOriginalName("電音部　日高零奈");
        setAltNames("デンオンブヒダカレイナ Denonbu Hidaka Reina");
        setDescription("jp",
                "@E：あなたの他の＜電音部＞のシグニ１体を対象とし、次の対戦相手のターン終了時まで、それのパワーを＋3000する。\n" +
                "@A #D @[エナゾーンから＜電音部＞のシグニ２枚をトラッシュに置く]@：あなたのトラッシュから#Gを持つシグニ１枚を対象とし、それを手札に加える。" +
                "~#：カードを１枚引く。このターン、あなたの手札にあるシグニは#Gを得る。"
        );

        setName("en", "DEN-ON-BU Reina Hidaka");
        setDescription("en",
                "@E: Another target <<DEN-ON-BU>> SIGNI on your field gets +3000 power until the end of your opponent's next end phase.\n@A #D @[Put two <<DEN-ON-BU>> SIGNI from your Ener Zone into your trash]@: Add target SIGNI with a #G from your trash to your hand." +
                "~#Draw a card. The SIGNI in your hand gain a #G this turn. "
        );
        
        setName("en_fan", "Reina Hidaka, Denonbu");
        setDescription("en_fan",
                "@E: Target 1 of your other <<Denonbu>> SIGNI, and until the end of your opponent's next turn, it gets +3000 power.\n" +
                "@A #D @[Put 2 <<Denonbu>> SIGNI from your ener zone into the trash]@: Target 1 #G @[Guard]@ SIGNI from your trash, and add it to your hand." +
                "~#Draw 1 card. This turn, all SIGNI in your hand gain #G @[Guard]@."
        );

		setName("zh_simplified", "电音部 日高零奈");
        setDescription("zh_simplified", 
                "@E :你的其他的<<電音部>>精灵1只作为对象，直到下一个对战对手的回合结束时为止，其的力量+3000。\n" +
                "@A 横置:从能量区把<<電音部>>精灵2张放置到废弃区从你的废弃区把持有#G的精灵1张作为对象，将其加入手牌。" +
                "~#抽1张牌。这个回合，你的手牌的精灵得到#G。（持有#G的精灵得到[[防御]]）\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.DENONBU);
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

            registerEnterAbility(this::onEnterEff);

            registerActionAbility(new AbilityCostList(new DownCost(), new TrashCost(2, new TargetFilter().SIGNI().withClass(CardSIGNIClass.DENONBU).fromEner())), this::onActionEff);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.PLUS).own().SIGNI().withClass(CardSIGNIClass.DENONBU).except(getCardIndex())).get();
            gainPower(target, 3000, ChronoDuration.nextTurnEnd(getOpponent()));
        }
        
        private void onActionEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).own().SIGNI().withState(CardStateFlag.CAN_GUARD).fromTrash()).get();
            addToHand(target);
        }

        private void onLifeBurstEff()
        {
            draw(1);

            ConstantAbilityShared attachedConstShared = new ConstantAbilityShared(new TargetFilter().own().SIGNI().fromHand(), new AbilityGainModifier(this::onAttachedConstEffModGetSample));
            attachPlayerAbility(getOwner(), attachedConstShared, ChronoDuration.turnEnd());
        }
        private Ability onAttachedConstEffModGetSample(CardIndex cardIndex)
        {
            return cardIndex.getIndexedInstance().registerStockAbility(new StockAbilityGuard());
        }
    }
}
