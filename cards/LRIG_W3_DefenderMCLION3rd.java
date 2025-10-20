package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardUnderType;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.cost.DownCost;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.game.FieldZone;

public final class LRIG_W3_DefenderMCLION3rd extends Card {

    public LRIG_W3_DefenderMCLION3rd()
    {
        setImageSets("WXDi-P15-010", "WXDi-P15-010U");

        setOriginalName("防衛者MC.LION-3rd");
        setAltNames("ボウエイシャエムシーリオンサード Boueisha Emu Shii Rion Saado");
        setDescription("jp",
                "@E：あなたのデッキの上からカードを５枚見る。その中から＜防衛派＞のシグニ１枚を場に出し、残りを好きな順番でデッキの一番下に置く。\n" +
                "@A $T1 @[アップ状態の＜防衛派＞のシグニ１体をダウンする]@：対戦相手のパワー8000以下のシグニ１体を対象とし、それを手札に戻す。\n" +
                "@A $G1 %W0：あなたのシグニゾーン１つに【ゲート】１つを置く。"
        );

        setName("en", "Defender MC LION - 3rd");
        setDescription("en",
                "@E: Look at the top five cards of your deck. Put a <<Defense Division>> SIGNI from among them onto your field. Put the rest on the bottom of your deck in any order.\n@A $T1 @[Down an upped <<Defense Division>> SIGNI]@: Return target SIGNI with power 8000 or less on your opponent's field to its owner's hand.\n@A $G1 %W0: Put a [[Gate]] in one of your SIGNI Zones."
        );
        
        setName("en_fan", "Defender MC.LION - 3rd");
        setDescription("en_fan",
                "@E: Look at the top 5 cards of your deck. Put 1 <<Defense Faction>> SIGNI from among them onto the field, and put the rest on the bottom of your deck in any order.\n" +
                "@A $T1 @[Down 1 of your upped <<Defense Faction>> SIGNI]@: Target 1 of your opponent's SIGNI with power 8000 or less, and return it to their hand.\n" +
                "@A $G1 %W0: Put 1 [[Gate]] on 1 of your SIGNI zones."
        );

		setName("zh_simplified", "防卫者MC.LION-3rd");
        setDescription("zh_simplified", 
                "@E :从你的牌组上面看5张牌。从中把<<防衛派>>精灵1张出场，剩下的任意顺序放置到牌组最下面。\n" +
                "@A $T1 竖直状态的<<防衛派>>精灵1只横置:对战对手的力量8000以下的精灵1只作为对象，将其返回手牌。\n" +
                "@A $G1 %W0:你的精灵区1个放置[[大门]]1个。\n"
        );

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.LION);
        setLRIGTeam(CardLRIGTeam.CARD_JOCKEY);
        setColor(CardColor.WHITE);
        setCost(Cost.color(CardColor.WHITE, 2));
        setLevel(3);
        setLimit(6);

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

            ActionAbility act1 = registerActionAbility(new DownCost(new TargetFilter().SIGNI().withClass(CardSIGNIClass.DEFENSE_FACTION).upped()), this::onActionEff1);
            act1.setUseLimit(UseLimit.TURN, 1);

            ActionAbility act2 = registerActionAbility(new EnerCost(Cost.color(CardColor.WHITE, 0)), this::onActionEff2);
            act2.setUseLimit(UseLimit.GAME, 1);
        }

        private void onEnterEff()
        {
            look(5);
            
            CardIndex cardIndex = playerTargetCard(0,1, new TargetFilter(TargetHint.FIELD).own().SIGNI().withClass(CardSIGNIClass.DEFENSE_FACTION).fromLooked().playable()).get();
            putOnField(cardIndex);
            
            while(getLookedCount() > 0)
            {
                cardIndex = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).own().fromLooked()).get();
                returnToDeck(cardIndex, DeckPosition.BOTTOM);
            }
        }

        private void onActionEff1()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).OP().SIGNI().withPower(0,8000)).get();
            addToHand(target);
        }

        private void onActionEff2()
        {
            FieldZone fieldZone = playerTargetZone(new TargetFilter(TargetHint.ZONE).own().SIGNI().not(new TargetFilter().withZoneObject(CardUnderType.ZONE_GATE))).get();
            attachZoneObject(fieldZone, CardUnderType.ZONE_GATE);
        }
    }
}
