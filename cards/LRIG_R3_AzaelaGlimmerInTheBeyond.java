package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.ConstantAbilityShared;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.modifiers.CostModifier;
import open.batoru.data.ability.modifiers.CostModifier.ModifierMode;

public final class LRIG_R3_AzaelaGlimmerInTheBeyond extends Card {

    public LRIG_R3_AzaelaGlimmerInTheBeyond()
    {
        setImageSets("WXDi-P16-010", "WXDi-P16-010U");
        setLinkedImageSets("WXDi-P16-TK01");

        setOriginalName("彼方へ輝く　アザエラ");
        setAltNames("カナタヘカガヤクアザエラ Kanata he Kagayaku Azaera");
        setDescription("jp",
                "@U：このルリグがアタックしたとき、対戦相手のエナゾーンにカードが２枚以上ある場合、対戦相手は自分のエナゾーンからカード１枚を選びトラッシュに置く。\n" +
                "@E: カードを１枚引き【エナチャージ１】をする。\n" +
                "@A $G1 %R0：クラフトの《インビンシブル・ストーリー》１枚をルリグデッキに加える。対戦相手のライフクロスが２枚以下の場合、このターン、そのピースの使用コストは%X減る。"
        );

        setName("en", "Azaela, Shining Beyond");
        setDescription("en",
                "@U: Whenever this LRIG attacks, if there are two or more cards in your opponent's Ener Zone, your opponent chooses a card from their Ener Zone and puts it into their trash.\n@E: Draw a card and [[Ener Charge 1]].\n@A $G1 %R0: Add an \"Invincible Story\" Craft to your LRIG Deck. If your opponent has two or fewer cards in their Life Cloth, the use cost of that PIECE is reduced by %X this turn."
        );
        
        setName("en_fan", "Azaela, Glimmer in the Beyond");
        setDescription("en_fan",
                "@U: Whenever this LRIG attacks, if there are 2 or more cards in your opponent's ener zone, your opponent chooses 1 card from their ener zone, and puts it into the trash.\n" +
                "@E: Draw 1 card and [[Ener Charge 1]].\n" +
                "@A $G1 %R0: Add 1 \"Invincible Story\" craft into your LRIG deck. If your opponent has 2 or less life cloth, this turn, its use cost is reduced by %X."
        );

		setName("zh_simplified", "向彼方辉耀 阿左伊来");
        setDescription("zh_simplified", 
                "@U :当这只分身攻击时，对战对手的能量区的牌在2张以上的场合，对战对手从自己的能量区选1张牌放置到废弃区。\n" +
                "@E :抽1张牌并[[能量填充1]]。\n" +
                "@A $G1 %R0:衍生的《インビンシブル・ストーリー》1张加入分身牌组。对战对手的生命护甲在2张以下的场合，这个回合，那张和音的使用费用减%X。\n"
        );

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.AZAELA);
        setLRIGTeam(CardLRIGTeam.MUGEN_SHOUJO);
        setColor(CardColor.RED);
        setCost(Cost.color(CardColor.RED, 2));
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

            registerAutoAbility(GameEventId.ATTACK, this::onAutoEff);

            registerEnterAbility(this::onEnterEff);

            ActionAbility act = registerActionAbility(new EnerCost(Cost.color(CardColor.RED, 0)), this::onActionEff);
            act.setUseLimit(UseLimit.GAME, 1);
        }
        
        private void onAutoEff()
        {
            if(getEnerCount(getOpponent()) >= 2)
            {
                CardIndex cardIndex = playerTargetCard(getOpponent(), new TargetFilter(TargetHint.BURN).own().fromEner()).get();
                trash(cardIndex);
            }
        }

        private void onEnterEff()
        {
            draw(1);
            enerCharge(1);
        }

        private void onActionEff()
        {
            CardIndex cardIndex = craft(getLinkedImageSets().get(0));
            if(returnToDeck(cardIndex, DeckPosition.TOP) && getLifeClothCount(getOpponent()) <= 2)
            {
                ConstantAbilityShared attachedConst = new ConstantAbilityShared(new TargetFilter().own().anyLocation().match(cardIndex),
                    new CostModifier(() -> new EnerCost(Cost.colorless(1)), ModifierMode.REDUCE)
                );
                attachPlayerAbility(getOwner(), attachedConst, ChronoDuration.turnEnd());
            }
        }
    }
}
