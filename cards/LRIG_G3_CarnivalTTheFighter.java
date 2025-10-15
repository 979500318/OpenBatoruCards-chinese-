package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.core.gameplay.rulechecks.RuleCheck.RuleCheckState;
import open.batoru.core.gameplay.rulechecks.player.PlayerRuleCheckRegistry.PlayerRuleCheckType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.DataTable;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.cost.EnerCost;

public final class LRIG_G3_CarnivalTTheFighter extends Card {

    public LRIG_G3_CarnivalTTheFighter()
    {
        setImageSets("WXDi-P15-008", "WXDi-P15-008U");

        setOriginalName("闘争者カーニバル　#T#");
        setAltNames("トウソウシャカーニバルワイルドテルセイロ Tousousha Kaanibaru Wairudo Teruseiro Wild Terceiro");
        setDescription("jp",
                "@E：あなたのトラッシュから＜闘争派＞のシグニを２枚まで対象とし、それらをエナゾーンに置く。\n" +
                "@A $T1 %X：あなたのエナゾーンから＜闘争派＞のシグニ１枚を対象とし、それを手札に加える。\n" +
                "@A $G1 %G0：#C #C #C #C #Cを得る。このゲームの間、あなたは#Cをスペルとシグニにしか支払えない。"
        );

        setName("en", "Warrior Carnival #T#");
        setDescription("en",
                "@E: Put up to two target <<War Division>> SIGNI from your trash into your Ener Zone.\n@A $T1 %X: Add target <<War Division>> SIGNI from your Ener Zone to your hand.\n@A $G1 %G0: Gain #C #C #C #C #C. You can only pay #C for spells and SIGNI for the duration of the game."
        );
        
        setName("en_fan", "Carnival #T#, The Fighter");
        setDescription("en_fan",
                "@E: Target up to 2 <<Struggle Faction>> SIGNI from your trash, and put them into the ener zone.\n" +
                "@A $T1 %X: Target 1 <<Struggle Faction>> SIGNI from your ener zone, and add it to your hand.\n" +
                "@A $G1 %G0: Gain #C #C #C #C #C. For the rest of the game, you may only spend #C on spells and SIGNI."
        );

		setName("zh_simplified", "斗争者嘉年华 #T#");
        setDescription("zh_simplified", 
                "@E :从你的废弃区把<<闘争派>>精灵2张最多作为对象，将这些放置到能量区。\n" +
                "@A $T1 %X:从你的能量区把<<闘争派>>精灵1张作为对象，将其加入手牌。\n" +
                "@A $G1 %G0得到#C #C #C #C #C。这场游戏期间，你只有把币:用在魔法和精灵才能支付。\n"
        );

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.CARNIVAL);
        setColor(CardColor.GREEN);
        setCost(Cost.color(CardColor.GREEN, 2));
        setLevel(3);
        setLimit(6);
        setCoins(2);

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

            ActionAbility act1 = registerActionAbility(new EnerCost(Cost.colorless(1)), this::onActionEff1);
            act1.setUseLimit(UseLimit.TURN, 1);

            ActionAbility act2 = registerActionAbility(new EnerCost(Cost.color(CardColor.GREEN, 0)), this::onActionEff2);
            act2.setUseLimit(UseLimit.GAME, 1);
        }

        private void onEnterEff()
        {
            DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.ENER).own().SIGNI().withClass(CardSIGNIClass.STRUGGLE_FACTION).fromTrash());
            putInEner(data);
        }
        
        private void onActionEff1()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).own().SIGNI().withClass(CardSIGNIClass.STRUGGLE_FACTION).fromEner()).get();
            addToHand(target);
        }

        private void onActionEff2()
        {
            gainCoins(5);

            addPlayerRuleCheck(PlayerRuleCheckType.CAN_SPEND_COINS, getOwner(), ChronoDuration.permanent(), data ->
                data.getSourceCardIndex().getIndexedInstance().matchesTypeByRef(data.getSourceAbility(), CardType.SPELL, CardType.SIGNI,CardType.RESONA) ? RuleCheckState.IGNORE : RuleCheckState.BLOCK
            );
        }
    }
}
