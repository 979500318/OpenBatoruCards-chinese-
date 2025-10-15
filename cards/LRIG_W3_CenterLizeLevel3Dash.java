package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.core.gameplay.rulechecks.RuleCheckData;
import open.batoru.core.gameplay.rulechecks.player.PlayerRuleCheckRegistry.PlayerRuleCheckType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.ConstantAbility;
import open.batoru.data.ability.ConstantAbilityShared;
import open.batoru.data.ability.cost.*;
import open.batoru.data.ability.modifiers.AbilityGainModifier;
import open.batoru.data.ability.modifiers.PlayerRuleCheckModifier;
import open.batoru.data.ability.stock.StockAbilityShadow;

public final class LRIG_W3_CenterLizeLevel3Dash extends Card {

    public LRIG_W3_CenterLizeLevel3Dash()
    {
        setImageSets("WXDi-CP01-005");

        setOriginalName("【センター】リゼ　レベル３'");
        setAltNames("センターリゼレベルサンダッシュ Sentaa Rize Reberu San Dasshu Center Lize 3 Dash");
        setDescription("jp",
                "@C：あなたが【ガード】する際、#Gを持つカードを１枚捨てる代わりに%Xを支払いコラボライバー１人とコラボしてもよい。\n" +
                "@E：コラボライバー２人を呼ぶ。\n" +
                "@A $G1 @[@|＃ヘルエスタ国営放送|@]@ %W0：あなたのデッキの上からカードを５枚見る。その中からシグニを１枚まで場に出し、残りを好きな順番でデッキの一番下に置く。次の対戦相手のターンの間、あなたのシグニは[[シャドウ（シグニ）]]を得る。"
        );

        setName("en", "[Center] Lize, Level 3'");
        setDescription("en",
                "@C: As you [[Guard]], you may pay %X and collaborate with a Collab Liver instead of discarding a card with a #G.\n@E: Invite two Collab Livers.\n@A $G1 #NationalBroadcastingofHelestaKingdom %W0: Look at the top five cards of your deck. Put up to one SIGNI from among them onto your field. Put the rest on the bottom of your deck in any order. During your opponent's next turn, SIGNI on your field gain [[Shadow -- SIGNI]]. "
        );
        
        setName("en_fan", "[Center] Lize Level 3'");
        setDescription("en_fan",
                "@C: You may [[Guard]] by paying %X and collabing with 1 CollaboLiver instead of discarding 1 card with #G @[Guard]@.\n" +
                "@E: Invite 2 CollaboLivers.\n" +
                "@A $G1 @[@|#``HelestaNationalBroadcast|@]@ %W0: Look at the top 5 cards of your deck. Put up to 1 SIGNI from among them onto your field, and put the rest on the bottom of your deck in any order. During your opponent's next turn, all of your SIGNI gain [[Shadow (SIGNI)]]."
        );

		setName("zh_simplified", "【核心】莉泽 等级3'");
        setDescription("zh_simplified", 
                "@C 你[[防御]]时，把持有#G的牌1张舍弃，作为替代，可以支付%X:并与联动主播1人联动。\n" +
                "@E :呼唤联动主播2人。\n" +
                "@A $G1 #赫露艾斯塔国营放送%W0:从你的牌组上面看5张牌。从中把精灵1张最多出场，剩下的任意顺序放置到牌组最下面。下一个对战对手的回合期间，你的精灵得到[[暗影（精灵）]]。（这个能力的使用后出场的精灵也给予影响）\n"
        );

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.LIZE);
        setLRIGTeam(CardLRIGTeam.SANBAKA);
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

            registerConstantAbility(new PlayerRuleCheckModifier<>(PlayerRuleCheckType.COST_TO_GUARD, TargetFilter.HINT_OWNER_OWN, this::onConstEffModGetSample));

            registerEnterAbility(this::onEnterEff);

            ActionAbility act = registerActionAbility(new EnerCost(Cost.color(CardColor.WHITE, 0)), this::onActionEff);
            act.setUseLimit(UseLimit.GAME, 1);
            act.setName("#Helesta National Broadcast");
        }

        private AbilityCost onConstEffModGetSample(RuleCheckData data)
        {
            return new AbilityORCost(AbilityORCost.REPLACE_DEFAULT, new AbilityANDCost(new EnerCost(Cost.colorless(1)), new CollaboLiverCost(1)));
        }

        private void onEnterEff()
        {
            inviteCollaboLivers(2);
        }

        private void onActionEff()
        {
            look(5);

            CardIndex cardIndex = playerTargetCard(0,1, new TargetFilter(TargetHint.FIELD).own().SIGNI().fromLooked().playable()).get();
            putOnField(cardIndex);

            while(getLookedCount() > 0)
            {
                cardIndex = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).own().fromLooked()).get();
                returnToDeck(cardIndex, DeckPosition.BOTTOM);
            }

            ConstantAbility attachedConst = new ConstantAbilityShared(new TargetFilter().own().SIGNI(), new AbilityGainModifier(this::onAttachedConstEffModGetSample));
            attachedConst.setCondition(this::onAttachedConstEffCond);

            attachPlayerAbility(getOwner(), attachedConst, ChronoDuration.nextTurnEnd(getOpponent()));
        }
        private ConditionState onAttachedConstEffCond()
        {
            return !isOwnTurn() ? ConditionState.OK : ConditionState.BAD;
        }
        private Ability onAttachedConstEffModGetSample(CardIndex cardIndex)
        {
            return cardIndex.getIndexedInstance().registerStockAbility(new StockAbilityShadow(this::onAttachedStockEffAddCond));
        }
        private ConditionState onAttachedStockEffAddCond(CardIndex cardIndexSource)
        {
            return CardType.isSIGNI(cardIndexSource.getCardReference().getType()) ? ConditionState.OK : ConditionState.BAD;
        }
    }
}
